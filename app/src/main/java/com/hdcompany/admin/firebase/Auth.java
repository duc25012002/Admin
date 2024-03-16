package com.hdcompany.admin.firebase;


import android.app.Activity;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.hdcompany.admin.MyApplication;
import com.hdcompany.admin.model.User;
import com.hdcompany.admin.utility.SportClothJsonConfig;

import org.checkerframework.checker.units.qual.N;

import java.util.HashMap;
import java.util.Objects;

public class Auth {
    public static FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseStorage sportClothesFireStorage(Context context) {
        return MyApplication.get(context).getSportClothsFirebaseStorage();
    }

    public static FirebaseDatabase sportClothsDatabase(Context context) {
        return MyApplication.get(context).getSportClothsDatabase();
    }

    public static Query sportClothsDBSportsReferenceQuery(@NonNull Context context) {
        return sportClothsDatabase(context).getReference("/sports");
    }

    public static Query sportClothsDBBookingReferenceQuery(@NonNull Context context) {
        return sportClothsDatabase(context).getReference("/booking");
    }

    public static Query sportClothsDBFeedbackReferenceQuery(@NonNull Context context) {
        return sportClothsDatabase(context).getReference("/feedback");
    }

    /*
    LOGIN USE EMAIL AND PASSWORD
     */
    synchronized public static FirebaseUser loginAuth(@NonNull Activity activity, User user) {
        firebaseAuth().signInWithEmailAndPassword(user.getUsername().trim(), user.getPassword().trim()).addOnCompleteListener(
                activity,
                task -> {
                    if (task.isSuccessful()) {

                        System.out.println("Sign in with Email & Password success!");
                        firebaseAuth().getCurrentUser();
                        Toast.makeText(activity, "Sign in with Email & Password success!", Toast.LENGTH_SHORT).show();

                    } else {
                        System.out.println("Sign in with Email & Password failed!");
                        user.setUsername("");
                        user.setPassword("");
                        Toast.makeText(activity, "Sign in with Email & Password failed!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        return firebaseAuth().getCurrentUser();
    }

    /*
    SIGN UP USE EMAIL AND PASSWORD
     */
    synchronized public static void signUpAuth(@NonNull Activity activity, User user) {

        firebaseAuth().createUserWithEmailAndPassword(user.getUsername().trim(), user.getPassword().trim()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        if(task.isComplete()){
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(activity, "Sign up with Email & Password success!",
                                    Toast.LENGTH_SHORT).show();
                            firebaseAuth().getCurrentUser();
                        }
                    } else {
                        Toast.makeText(activity, "Sign up with Email & Password failed!",
                                Toast.LENGTH_SHORT).show();
                        user.setUsername("");
                        user.setPassword("");
                    }
                }
        );
    }
    /*
    Get product in a limited items
     */
    public static Query getLimitedSportClothes(String key, @NonNull Context context) {
        if (key == null) {
            return sportClothsDBSportsReferenceQuery(context).orderByKey().limitToFirst(20);
        }
        return sportClothsDBSportsReferenceQuery(context).orderByKey().startAfter(key).limitToFirst(20);
    }

    public static Query getLimitedFeedbackClothes(String key, @NonNull Context context) {
        if (key == null) {
            return sportClothsDBFeedbackReferenceQuery(context).orderByKey().limitToFirst(20);
        }
        return sportClothsDBFeedbackReferenceQuery(context).orderByKey().startAfter(key).limitToFirst(20);
    }

    public static Query getLimitedBookingClothes(String key, @NonNull Context context) {
        if (key == null) {
            return sportClothsDBBookingReferenceQuery(context).orderByKey().limitToFirst(20);
        }
        return sportClothsDBBookingReferenceQuery(context).orderByKey().startAfter(key).limitToFirst(20);
    }
}
