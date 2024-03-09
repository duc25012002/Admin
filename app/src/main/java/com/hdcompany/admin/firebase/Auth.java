package com.hdcompany.admin.firebase;



import android.app.Activity;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.hdcompany.admin.model.User;

import java.util.HashMap;
import java.util.Objects;

public class Auth {
    public static FirebaseAuth firebaseAuth(){
      return FirebaseAuth.getInstance();
    }
    public static FirebaseDatabase firebaseDatabase() { return FirebaseDatabase.getInstance();
    }
    public static GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("994449845746-jk1r9sem3313o2o07nqp86t9ookcq9kv.apps.googleusercontent.com")
            .requestEmail().build();

    public static FirebaseUser firebaseAuthen(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        String username = "ERROR";
       firebaseAuth().signInWithCredential(credential).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = firebaseAuth().getCurrentUser();
                        HashMap<String, Object> map = new HashMap<>();
                        assert user != null;
                        map.put("id", user.getUid());
                        map.put("name", user.getDisplayName());
                        map.put("profile", Objects.requireNonNull(user.getPhotoUrl()).toString());
                        firebaseDatabase().getReference().child("users").child(user.getUid()).setValue(map);
                    }
                }
        );
    return firebaseAuth().getCurrentUser();
    }
    public static FirebaseUser loginAuth(Activity activity, User user){
        firebaseAuth().signInWithEmailAndPassword(user.getUsername().trim(), user.getPassword().trim()).addOnCompleteListener(
                activity,
                task -> {
                    if (task.isSuccessful()){

                            System.out.println("Sign in with Email & Password success!");
//                            FirebaseUser signed = firebaseAuth.getCurrentUser();
                            Toast.makeText(activity, "Sign in with Email & Password success!", Toast.LENGTH_SHORT).show();

                    }else{
                        System.out.println("Sign in with Email & Password failed!");
                        user.setUsername("");
                        user.setPassword("");
                        Toast.makeText(activity, "Sign in with Email & Password failed!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        return firebaseAuth().getCurrentUser();
    }
    public static FirebaseUser signUpAuth(Activity activity, User user){
        firebaseAuth().createUserWithEmailAndPassword(user.getUsername().trim(), user.getPassword().trim()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {

                         // Sign in success, update UI with the signed-in user's information
                         Toast.makeText(activity, "Sign up with Email & Password success!",
                                 Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "createUserWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        updateUI(user);

                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(activity, "Sign up with Email & Password failed!",
                                Toast.LENGTH_SHORT).show();
                        user.setUsername("");
                        user.setPassword("");
//                        updateUI(null);
                    }
                }
        );
        return firebaseAuth().getCurrentUser();
    }
}
