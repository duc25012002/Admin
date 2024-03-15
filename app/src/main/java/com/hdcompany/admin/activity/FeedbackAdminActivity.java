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
import com.hdcompany.admin.adapter.BookingsAdapter;
import com.hdcompany.admin.adapter.FeedbackAdapter;
import com.hdcompany.admin.databinding.ActivityBookingAdminBinding;
import com.hdcompany.admin.databinding.ActivityFeedbackAdminBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.Booking;
import com.hdcompany.admin.model.Feedback;
import com.hdcompany.admin.utility.GridSpace;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdminActivity extends AppCompatActivity {

    private ActivityFeedbackAdminBinding binding;
    private FeedbackAdapter feedBackAdapter;
    private String key = null;
    private boolean loadState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitRecyclerViewAdapter();
        getSportsFromFirebase();
        binding.recyclerViewFeedback.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        Auth.getLimitedFeedbackClothes(key, this).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    List<Feedback> feedbackList = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        key = data.getKey();
                            Feedback feedback = data.getValue(Feedback.class);
                        feedback.setId(key);
                        feedbackList.add(feedback);
                        System.out.println("KEY : " + key);
                    }
                    feedBackAdapter.setItems(feedbackList);
                    feedBackAdapter.notifyDataSetChanged();
                    loadState = false;
                    for (Feedback sp : feedbackList) {
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

        binding.recyclerViewFeedback.setLayoutManager(linearLayout);
        binding.recyclerViewFeedback.addItemDecoration(new GridSpace(2, 20, true));
        /*
        Set event click
         */

        feedBackAdapter = new FeedbackAdapter(new FeedbackAdapter.onClick() {
            @Override
            public void onItemClick(Feedback feedback) {
                Toast.makeText(FeedbackAdminActivity.this, "Soon", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        Bind List
         */
        binding.recyclerViewFeedback.setAdapter(feedBackAdapter);
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
                Feedback deletedItem = feedBackAdapter.getFeedback(position);

                // Remove the item from the adapter
                feedBackAdapter.getItems().remove(position);
                feedBackAdapter.notifyItemRemoved(position);

                // Delete the item from Firebase Realtime Database
                DatabaseReference databaseReference = Auth.sportClothsDatabase(FeedbackAdminActivity.this).getReference("/feedback"); // Change "sport_clothes" to your actual Firebase node
                databaseReference.child(deletedItem.getId()).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data deleted successfully
                                Toast.makeText(FeedbackAdminActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to delete data
                                Toast.makeText(FeedbackAdminActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                                // Add the item back to the adapter if deletion failed
                                feedBackAdapter.getItems().add(position, deletedItem);
                                feedBackAdapter.notifyItemInserted(position);
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
                    Drawable deleteIcon = ContextCompat.getDrawable(FeedbackAdminActivity.this, R.drawable.trash_can);
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

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewFeedback);
    }
}