package com.hdcompany.admin.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.hdcompany.admin.BR;
import com.hdcompany.admin.utility.Constant;

import java.io.Serializable;
import java.util.List;

public class Product extends BaseObservable implements Serializable {
    private String product_id;
    private String name;
    private String description;
    private double unit_price;
    private int sold_quantity;
    private int quantity_in_stock;
    private String seller;
    private String seller_address;


    private List<String> images;

    public Product() {
    }

    public Product(String product_id, String name, String description, double unit_price, int sold_quantity, int quantity_in_stock, String seller, String seller_address, List<String> images) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
        this.sold_quantity = sold_quantity;
        this.quantity_in_stock = quantity_in_stock;
        this.seller = seller;
        this.seller_address = seller_address;
        this.images = images;
    }

    @Bindable
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
        notifyPropertyChanged(BR.product_id);
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
    @Bindable
    public double getUnit_price() {
        return unit_price ;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
        notifyPropertyChanged(BR.unit_price);
    }
    @Bindable
    public int getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
        notifyPropertyChanged(BR.sold_quantity);
    }
    @Bindable
    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
        notifyPropertyChanged(BR.quantity_in_stock);
    }
    @Bindable
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
        notifyPropertyChanged(BR.seller);
    }
    @Bindable
    public String getSeller_address() {
        return seller_address;
    }

    public void setSeller_address(String seller_address) {
        this.seller_address = seller_address;
        notifyPropertyChanged(BR.seller_address);
    }
    @Bindable
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }
}
