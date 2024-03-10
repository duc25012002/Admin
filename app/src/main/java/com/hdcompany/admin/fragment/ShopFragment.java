package com.hdcompany.admin.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hdcompany.admin.MyApplication;
import com.hdcompany.admin.R;
import com.hdcompany.admin.activity.ProductDetailsActivity;
import com.hdcompany.admin.adapter.ProductLoadStateRCVAdapter;
import com.hdcompany.admin.adapter.ProductRCVAdapter;
import com.hdcompany.admin.databinding.FragmentShopBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.listener.IOnClickListener;
import com.hdcompany.admin.model.Product;
import com.hdcompany.admin.utility.Constant;
import com.hdcompany.admin.utility.GridSpace;
import com.hdcompany.admin.utility.ProductComparator;
import com.hdcompany.admin.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private FragmentShopBinding shopBinding;
    private Context context;
    private ProductRCVAdapter shopAdapter;
    /*
    Key for limited loading data
     */
    private String key = null;
    private boolean loadState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shopBinding = FragmentShopBinding.inflate(inflater,container,false);
        this.context = shopBinding.getRoot().getContext();

        shopBinding.message.setOnClickListener(v->{
            Toast.makeText(context, "Soon", Toast.LENGTH_SHORT).show();
            Utility.hideSoftKeyboard(getActivity());

        });

        InitRecyclerViewAdapter();
        getProductsFromFirebase();
        return shopBinding.getRoot();
    }

    private void getProductsFromFirebase(){
        Auth.getLimitedProducts(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> products =  new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    Product product = data.getValue(Product.class);
                    products.add(product);
                    key = data.getKey();
                    System.out.println("KEY : " + key);
                }
                shopAdapter.setProducts(products);
                shopAdapter.notifyDataSetChanged();
                loadState = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        MyApplication.get(this.context).getProductsDBRef().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
//                    Product product = dataSnapshot.getValue(Product.class);
//                    if(product == null)return;
//                    products.add(0,product);
//                    System.out.println("ProductName: " + product.getName());
//                }
//                InitRecyclerViewAdapter();
//                shopAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(context, Constant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void InitRecyclerViewAdapter(){
        /*
        Setting recyclerView
         */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.context,2);
        shopBinding.shopRecyclerView.setLayoutManager(gridLayoutManager);
        shopBinding.shopRecyclerView.addItemDecoration(new GridSpace(2,20,true));
        /*
        Set event click
         */
        shopAdapter = new ProductRCVAdapter(new IOnClickListener() {
            @Override
            public void onClickItemProduct(Product product) {
                Toast.makeText(context, "Soon", Toast.LENGTH_SHORT).show();
                Utility.hideSoftKeyboard(getActivity());
                Intent i = new Intent(getActivity(), ProductDetailsActivity.class);
                i.putExtra("product",product);
                getActivity().startActivity(i);
            }
        });
        /*
        Bind List
         */
        shopBinding.shopRecyclerView.setAdapter(shopAdapter);
        /*
        Adding spacing between item in list
         */
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return shopAdapter.getItemViewType(position) == ProductRCVAdapter.LOADING_ITEM?1:2;
            }
        });

        /*
        Loading state wile scrolling
         */
        shopBinding.shopRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager instanceLayoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
                int TotalItem = instanceLayoutManager.getItemCount();
                int LastItemVisible = instanceLayoutManager.findLastCompletelyVisibleItemPosition();
                if(TotalItem < (LastItemVisible + 3)){
                    loadState = true;
                    getProductsFromFirebase();
                }
            }
        });
    }
}