package com.hdcompany.admin.adapter;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdcompany.admin.databinding.SportClothesItemBinding;
import com.hdcompany.admin.listener.IOnClickListener;
import com.hdcompany.admin.model.SportClothes;
import com.hdcompany.admin.utility.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class SportClothesListAdapter extends RecyclerView.Adapter<SportClothesListAdapter.SportClothesViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int PRODUCT_ITEM = 1;

    private boolean is_trash_can = false;

    private List<SportClothes> sportClothesList = new ArrayList<>();
    private IOnClickListener clickListener;

    public SportClothesListAdapter(@Nonnull IOnClickListener listener) {
        clickListener = listener;
    }
    public void setProducts(List<SportClothes> items) {
        sportClothesList.addAll(items);
    }
    public SportClothes getSportCloth(int index) {
        return sportClothesList.get(index);
    } public List<SportClothes> getSportClothesList() {
        return sportClothesList;
    }

    @NonNull
    @Override
    public SportClothesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SportClothesItemBinding binding = SportClothesItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new SportClothesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SportClothesViewHolder holder, int position) {
        SportClothes product = sportClothesList.get(position);
        for(int i=0; i<10;i++){
            System.out.println("CHECK IMAGE: "+product.getImage());
        }
        String link_img = product.getBanner();
        Glide.with(holder.binding.imageViewProduct).load(link_img).into(holder.binding.imageViewProduct);
        holder.binding.textViewUnitPrice.setText("Price: " + product.getPrice() + Constant.CURRENCY);
        holder.binding.textViewSaleSportClothes.setText(product.getSale() + "% Sale");
        holder.binding.textViewNameSportClothes.setText(product.getName());
        holder.binding.itemSport.setOnClickListener(v->this.clickListener.onClickItemSportClothes(product));
        if(is_trash_can){
            holder.binding.trashCan.setVisibility(View.VISIBLE);
        }else{
            holder.binding.trashCan.setVisibility(View.GONE);
        }
    }

    public void setTrashCan(boolean visible){
        this.is_trash_can = visible;
    }

    @Override
    public int getItemCount() {
        return sportClothesList == null ? 0: sportClothesList.size();
    }

    public class SportClothesViewHolder extends RecyclerView.ViewHolder {
        private SportClothesItemBinding binding;
        public SportClothesViewHolder(@Nonnull SportClothesItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
