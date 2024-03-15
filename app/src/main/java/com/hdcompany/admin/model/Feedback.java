package com.hdcompany.admin.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.hdcompany.admin.BR;

import java.io.Serializable;

public class Feedback extends BaseObservable implements Serializable {
    private String id;
    private String comment;
    private String email;
    private String name; // buyer name
    private String phone;

    public Feedback(){

    }
    public Feedback(String comment, String email, String name, String phone) {
        this.comment = comment;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }
}
