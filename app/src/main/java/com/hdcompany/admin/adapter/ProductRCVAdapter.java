package com.hdcompany.admin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hdcompany.admin.databinding.ProductItemRcvBinding;
import com.hdcompany.admin.listener.IOnClickListener;
import com.hdcompany.admin.model.Product;
import com.hdcompany.admin.utility.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import kotlin.coroutines.CoroutineContext;

public class ProductRCVAdapter extends RecyclerView.Adapter<ProductRCVAdapter.ProductViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int PRODUCT_ITEM = 1;

    private List<Product> products = new ArrayList<>();
    private IOnClickListener clickListener;

    public ProductRCVAdapter(@Nonnull IOnClickListener listener) {
        clickListener = listener;
    }
    public void setProducts(List<Product> items) {
        products.addAll(items);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemRcvBinding binding = ProductItemRcvBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        for(int i=0; i<10;i++){
            System.out.println("CHECKNULL"+product.getName());
        }
        if(product != null){
            String link_img = product.getImages().get(0);
            Glide.with(holder.productItemRcvBinding.imageViewProduct).load(link_img).into(holder.productItemRcvBinding.imageViewProduct);
            holder.productItemRcvBinding.textViewUnitPrice.setText(product.getUnit_price() + Constant.CURRENCY);
            holder.productItemRcvBinding.textViewSoldProduct.setText(product.getSold_quantity() + " Sold");
            holder.productItemRcvBinding.textViewNameProduct.setText(product.getName());
            holder.productItemRcvBinding.itemProduct.setOnClickListener(v->this.clickListener.onClickItemProduct(product));
        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0: products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ProductItemRcvBinding productItemRcvBinding;
        public ProductViewHolder(@Nonnull ProductItemRcvBinding binding){
            super(binding.getRoot());
            this.productItemRcvBinding = binding;
        }

    }
}
