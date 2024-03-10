package com.hdcompany.admin.utility;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hdcompany.admin.model.Product;

public class ProductComparator extends DiffUtil.ItemCallback<Product> {
    @Override
    public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
        return oldItem.getProduct_id().equals(newItem.getProduct_id());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
        return oldItem.getProduct_id().equals(newItem.getProduct_id());
    }
}
