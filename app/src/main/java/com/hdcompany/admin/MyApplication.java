package com.hdcompany.admin;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class MyApplication extends Application {
    private FirebaseDatabase sportClothsDatabase;
    private FirebaseStorage sportClothsFirebaseStorage;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance();
        sportClothsDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance());
        sportClothsFirebaseStorage = FirebaseStorage.getInstance(FirebaseApp.getInstance());

    }

    public FirebaseStorage getSportClothsFirebaseStorage(){
        return sportClothsFirebaseStorage;
    }
    public FirebaseDatabase getSportClothsDatabase() {
        return sportClothsDatabase;
    }

    public DatabaseReference getSportClothsSportsDBRef() {
        return sportClothsDatabase.getReference("/sports");
    }

    public DatabaseReference getSportClothsBookingDBRef() {
        return sportClothsDatabase.getReference("/booking");
    }

    public DatabaseReference getSportClothsFeedbackDBRef() {
        return sportClothsDatabase.getReference("/feedback");
    }
}
