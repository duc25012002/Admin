package com.hdcompany.admin.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.hdcompany.admin.BR;

import java.io.Serializable;

public class Image extends BaseObservable implements Serializable {
    private int id;
    private String url;
    public Image(){

    }
    public Image(int id,String url){
        this.id = id;
        this.url = url;
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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }
}
