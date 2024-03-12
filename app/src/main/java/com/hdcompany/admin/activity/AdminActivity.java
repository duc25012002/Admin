package com.hdcompany.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hdcompany.admin.R;
import com.hdcompany.admin.adapter.SportClothesListAdapter;
import com.hdcompany.admin.databinding.ActivityAdminBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.listener.IOnClickListener;
import com.hdcompany.admin.model.Product;
import com.hdcompany.admin.model.SportClothes;
import com.hdcompany.admin.utility.GridSpace;
import com.hdcompany.admin.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding adminBinding;
    /*
    Key for limited loading data
     */
    private String key = null;
    private boolean loadState = false;

    private SportClothesListAdapter sportClothesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(adminBinding.getRoot());
        InitRecyclerViewAdapter();
        getSportsFromFirebase();
        adminBinding.sportRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager instanceLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int TotalItem = instanceLayoutManager.getItemCount();
                int LastItemVisible = instanceLayoutManager.findLastCompletelyVisibleItemPosition();
                if (TotalItem < (LastItemVisible + 5)) {
                    if (!loadState) {
                        loadState = true;
                        getSportsFromFirebase();
                    }
                }
            }
        });
    }

    private void getSportsFromFirebase() {
        Auth.getLimitedSportClothes(key, this).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    List<SportClothes> sportClothes = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        SportClothes sportCloth = data.getValue(SportClothes.class);
                        sportClothes.add(sportCloth);
                        key = data.getKey();
                        System.out.println("KEY : " + key);
                    }
                    sportClothesListAdapter.setProducts(sportClothes);
                    sportClothesListAdapter.notifyDataSetChanged();
                    loadState = false;
                    for (SportClothes sp : sportClothes) {
                        System.out.println("SportClothes: " + sp.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void InitRecyclerViewAdapter() {
        /*
        Setting recyclerView
         */
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);

        adminBinding.sportRecyclerView.setLayoutManager(linearLayout);
        adminBinding.sportRecyclerView.addItemDecoration(new GridSpace(2, 20, true));
        /*
        Set event click
         */
        sportClothesListAdapter = new SportClothesListAdapter(new IOnClickListener() {
            @Override
            public void onClickItemProduct(Product product) {
            }

            @Override
            public void onClickItemSportClothes(SportClothes sportClothes) {
                Toast.makeText(AdminActivity.this, "Forward to SportClothesDetailed", Toast.LENGTH_SHORT).show();
                Utility.hideSoftKeyboard(AdminActivity.this);
            }
        });
        /*
        Bind List
         */
        adminBinding.sportRecyclerView.setAdapter(sportClothesListAdapter);
        attachSwipeToDelete();
        
    }

    private void attachSwipeToDelete() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Handle swipe to delete here
                int position = viewHolder.getAdapterPosition();
                // Retrieve the item from the adapter
                SportClothes deletedItem = sportClothesListAdapter.getSportCloth(position);

                // Remove the item from the adapter
                sportClothesListAdapter.getSportClothesList().remove(position);
                sportClothesListAdapter.notifyItemRemoved(position);

                // Delete the item from Firebase Realtime Database
                DatabaseReference databaseReference = Auth.sportClothsDatabase(AdminActivity.this).getReference("/sports"); // Change "sport_clothes" to your actual Firebase node
                databaseReference.child(String.valueOf(deletedItem.getId())).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data deleted successfully
                                Toast.makeText(AdminActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to delete data
                                Toast.makeText(AdminActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                                // Add the item back to the adapter if deletion failed
                                sportClothesListAdapter.getSportClothesList().add(position, deletedItem);
                                sportClothesListAdapter.notifyItemInserted(position);
                            }
                        });
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Draw the delete button when swiping
                View itemView = viewHolder.itemView;
                if (dX < 0) { // Swiping to the left
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, paint);
                    Drawable deleteIcon = ContextCompat.getDrawable(AdminActivity.this, R.drawable.trash_can);
                    if (deleteIcon != null) {
                        int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                        int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                        int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                        int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
                        int iconRight = itemView.getRight() - iconMargin;
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                        deleteIcon.draw(c);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(adminBinding.sportRecyclerView);
    }

}