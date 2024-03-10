package com.hdcompany.admin.adapter;

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

import java.util.List;

import javax.annotation.Nonnull;

import kotlin.coroutines.CoroutineContext;

public class ProductRCVAdapter extends RecyclerView.Adapter<ProductRCVAdapter.ProductViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int PRODUCT_ITEM = 1;

    private static List<Product> products;
    private IOnClickListener clickListener;

    public ProductRCVAdapter(@NonNull List<Product> products, @Nonnull IOnClickListener listener) {
        this.products = products;
        clickListener = listener;
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
            holder.productItemRcvBinding.setProduct(product);
            String link_img = "https://konachan.com/image/b909ff85dd5558140ff05798e0e4eecc/Konachan.com%20-%20373493%20boat%20braids%20dark%20dragon%20dress%20fire%20leo-dont-want-to-be-a-painter%20long_hair%20original%20ponytail%20sword%20tattoo%20water%20weapon.jpg";
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
