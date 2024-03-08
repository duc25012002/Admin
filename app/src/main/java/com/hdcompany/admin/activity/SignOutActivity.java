package com.hdcompany.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.hdcompany.admin.R;
import com.hdcompany.admin.databinding.ActivitySignOutBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.User;

public class SignOutActivity extends AppCompatActivity {

    private User user = new User();
    private ActivitySignOutBinding signOutBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signOutBinding = ActivitySignOutBinding.inflate(getLayoutInflater());
        setContentView(signOutBinding.getRoot());
        signOutBinding.setUser(user);
        setOnClick();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.setUsername(Auth.firebaseAuth.getCurrentUser().getEmail());
            }
        },1500);
    }

    private void setOnClick(){
        signOutBinding.signOutButton.setOnClickListener(v->{
            Auth.firebaseAuth.signOut();
        });
    }
}