package com.hdcompany.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hdcompany.admin.MainActivity;
import com.hdcompany.admin.R;
import com.hdcompany.admin.adapter.MyImageProductViewPager2Adapter;
import com.hdcompany.admin.adapter.ProductRCVAdapter;
import com.hdcompany.admin.databinding.ActivityProductDetailsBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.listener.IOnClickListener;
import com.hdcompany.admin.model.Product;
import com.hdcompany.admin.utility.Constant;
import com.hdcompany.admin.utility.GridSpace;
import com.hdcompany.admin.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding productDetailsBinding;

    private Product product = null;
    private List<String> productImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDetailsBinding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(productDetailsBinding.getRoot());
        productDetailsBinding.backButton.setOnClickListener(v -> {
            this.finish();
        });
        productDetailsBinding.scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            if (scrollY - oldScrollY > 10) {
                // Đang scroll xuống
                // Đặt code xử lý khi scroll xuống ở đây
                productDetailsBinding.navProductDetailView.setVisibility(View.GONE);
            } else if (oldScrollY - scrollY > 10) {
                // Đang scroll lên
                // Đặt code xử lý khi scroll lên ở đây
                productDetailsBinding.navProductDetailView.setVisibility(View.VISIBLE);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            try {
                do {
                    product = getIntent().getSerializableExtra("product", Product.class);
                } while (product == null);
                setDisplay();
                for (int i = 0; i < 11; i++) {
                    System.out.println("GOT INTENT PRODUCT DATA: " + product.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setDisplay() {

        productDetailsBinding.txtPrice.setText(product.getUnit_price() + Constant.CURRENCY);
        productDetailsBinding.txtProductDescription.setText(product.getDescription());
        productDetailsBinding.txtProductName.setText(product.getName());
        productDetailsBinding.txtShopSeller.setText(product.getSeller());
        productDetailsBinding.txtShopAddress.setText((product.getSeller_address()));
        productDetailsBinding.txtSoldQuantity.setText(product.getSold_quantity() + " SOLD");
        do{
            productImg = product.getImages();
        }while(productImg == null);
        MyImageProductViewPager2Adapter imgAdapter = new MyImageProductViewPager2Adapter(this,productImg);
        productDetailsBinding.productImageListViewPager2.setAdapter(imgAdapter);
    }
}