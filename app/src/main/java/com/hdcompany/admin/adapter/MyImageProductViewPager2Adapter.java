package com.hdcompany.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.hdcompany.admin.R;
import com.hdcompany.admin.databinding.ProductImagesDetailItemBinding;

import org.checkerframework.checker.units.qual.N;

import java.util.List;

public class MyImageProductViewPager2Adapter extends RecyclerView.Adapter<MyImageProductViewPager2Adapter.ViewHolderer> {
    private List<String> imagePaths;
    private Context context;

    private int currentImage, totalImage;

    public MyImageProductViewPager2Adapter(@NonNull Context context, @NonNull List<String> imgs){
        this.context = context;
        this.imagePaths = imgs;
        totalImage = this.imagePaths.size();
    }
    @NonNull
    @Override
    public ViewHolderer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductImagesDetailItemBinding binding = ProductImagesDetailItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderer(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderer holder, int position) {
        Glide.with(context).load(imagePaths.get(position)).into(holder.binding.imageViewSportClothes);
        holder.binding.totalImage.setText(String.valueOf(totalImage));
        currentImage = position + 1;
        holder.binding.currentImageNumber.setText(String.valueOf(currentImage));
    }

    @Override
    public int getItemCount() {
        return totalImage;
    }


    public class ViewHolderer extends RecyclerView.ViewHolder{
        private ProductImagesDetailItemBinding binding;
        public ViewHolderer(@NonNull ProductImagesDetailItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
//    public MyImageProductViewPager2Adapter(Context context, List<String> imagePaths) {
//        super();
//        this.context = context;
//        this.imagePaths = imagePaths;
//    }
//
//    @Override
//    public int getCount() {
//        totalImage = imagePaths.size();
//        return imagePaths.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view.equals(object);
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        ProductImagesDetailItemBinding binding = ProductImagesDetailItemBinding.inflate(LayoutInflater.from(context), container, false);
//        View itemView = binding.getRoot();
//        Glide.with(context).load(imagePaths.get(position)).into(binding.imageViewProduct);
//        binding.totalImage.setText(String.valueOf(totalImage));
//        binding.currentImageNumber.setText(String.valueOf(position));
//        container.addView(itemView);
//        return itemView;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView((View) object);
//    }
    /*
    Trong đoạn mã trên:

    imagePaths: Danh sách các đường dẫn ảnh.
    instantiateItem(): Phương thức này được sử dụng để khởi tạo một item trong ViewPager2. Chúng ta sử dụng thư viện Glide để tải ảnh từ đường dẫn và hiển thị vào ImageView.
    destroyItem(): Phương thức này được sử dụng để loại bỏ item khi không cần thiết nữa.
     */
}
