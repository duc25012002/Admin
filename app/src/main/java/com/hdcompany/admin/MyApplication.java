package com.hdcompany.admin;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hdcompany.admin.utility.AdminJsonConfig;
import com.hdcompany.admin.utility.Constant;
import com.hdcompany.admin.utility.SportClothJsonConfig;

public class MyApplication extends Application {
    private FirebaseDatabase adminDatabase;
    private FirebaseDatabase sportClothsDatabase;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo Firebase cho từng cấu hình
        FirebaseOptions adminOption = new FirebaseOptions.Builder()
                .setProjectId(AdminJsonConfig.PROJECT_ID)
                .setStorageBucket(AdminJsonConfig.STORAGE_BUCKET)
                .setApplicationId(AdminJsonConfig.APPLICATION_ID)
                .setApiKey(AdminJsonConfig.API_KEY)
                .setDatabaseUrl(AdminJsonConfig.FIREBASE_URL)
                .build();
        FirebaseApp.initializeApp(this, adminOption, "Admin");

        adminDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("Admin"));

        // Khởi tạo Firebase cho từng cấu hình
        FirebaseOptions sportClothsOption = new FirebaseOptions.Builder()
                .setProjectId(SportClothJsonConfig.PROJECT_ID)
                .setStorageBucket(SportClothJsonConfig.STORAGE_BUCKET)
                .setApplicationId(SportClothJsonConfig.APPLICATION_ID)
                .setApiKey(SportClothJsonConfig.API_KEY)
                .setDatabaseUrl(SportClothJsonConfig.FIREBASE_URL)
                .build();
        FirebaseApp.initializeApp(this, sportClothsOption, "SportClothes");

        sportClothsDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("SportClothes"));

    }

    public FirebaseDatabase getAdminDatabase() {
        return adminDatabase;
    }

    public FirebaseDatabase getSportClothsDatabase() {
        return sportClothsDatabase;
    }

    public DatabaseReference getAdminProductsDBRef() {
        return adminDatabase.getReference("/products");
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
