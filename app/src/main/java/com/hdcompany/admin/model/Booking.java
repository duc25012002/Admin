package com.hdcompany.admin.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.hdcompany.admin.BR;
import com.hdcompany.admin.utility.Constant;

import java.io.Serializable;

public class Booking extends BaseObservable implements Serializable {
    private String wrapId;
    private String id;
    private String name;
    private String address;
    private String phone;
    private String size;
    private String sportCloth; // product name
    private int amount;
    private int payment; // banking or cash

    public Booking(){

    }
    public Booking(String id, String name, String address, String phone, String size, String sportCloth, int amount, int payment) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone= phone;
        this.size = size;
        this.sportCloth = sportCloth;
        this.amount = amount;
        this.payment = payment;
    }

    public String getWrapId() {
        return wrapId;
    }

    public void setWrapId(String wrapId) {
        this.wrapId = wrapId;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }
    @Bindable
    public String getSportCloth() {
        return sportCloth;
    }

    public void setSportCloth(String sportCloth) {
        this.sportCloth = sportCloth;
        notifyPropertyChanged(BR.sportCloth);
    }
    @Bindable
    public String getAmount() {
        return String.valueOf(amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }
    @Bindable
    public String getPayment() {
        switch(payment){
            case 2: return Constant.PAYMENT_METHOD_BANKING;
            default: return Constant.PAYMENT_METHOD_CASH;
        }
    }

    public void setPayment(int payment) {
        this.payment = payment;
        notifyPropertyChanged(BR.payment);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
        notifyPropertyChanged(BR.size);
    }
}
