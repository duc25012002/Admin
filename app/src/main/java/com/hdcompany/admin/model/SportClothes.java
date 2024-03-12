package com.hdcompany.admin.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;
import java.util.List;

public class SportClothes extends BaseObservable implements Serializable {
    private int id;
    private String name;
    private String image;

    private String banner;

    private String description;
    private int price;
    private int sale;
    private int count;
    private int totalPrice;
    private boolean popular;
    private List<Image> images;

    public SportClothes() {
    }

    public SportClothes(int id, String name, String image, String banner, String description, int price, int sale, int count, int totalPrice, boolean popular, List<Image> images) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.banner = banner;
        this.description = description;
        this.price = price;
        this.sale = sale;
        this.count = count;
        this.totalPrice = totalPrice;
        this.popular = popular;
        this.images = images;
    }

    @Bindable
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
        notifyPropertyChanged(BR.banner);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
        notifyPropertyChanged(BR.sale);
    }

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
        notifyPropertyChanged(BR.totalPrice);
    }

    @Bindable
    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
        notifyPropertyChanged(BR.popular);
    }

    @Bindable
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Override
    public String toString() {
        return "SportClothes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", sale=" + sale +
                ", count=" + count +
                ", totalPrice=" + totalPrice +
                ", popular=" + popular +
                ", images=" + images +
                '}';
    }
}
