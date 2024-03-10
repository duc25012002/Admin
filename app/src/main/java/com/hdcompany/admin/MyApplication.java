package com.hdcompany.admin;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hdcompany.admin.utility.Constant;

public class MyApplication extends Application {
    private FirebaseDatabase firebaseDatabase;
    public static MyApplication get (Context context){
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(Constant.FIREBASE_URL);
    }

    public DatabaseReference getProductsDBRef(){
        return firebaseDatabase.getReference("/products");
    }
}
