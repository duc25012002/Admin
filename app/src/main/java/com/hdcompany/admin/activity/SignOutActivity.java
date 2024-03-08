package com.hdcompany.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hdcompany.admin.R;
import com.hdcompany.admin.databinding.ActivitySignOutBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.User;

public class SignOutActivity extends AppCompatActivity {

    private User user;
    private ActivitySignOutBinding signOutBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signOutBinding = ActivitySignOutBinding.inflate(getLayoutInflater());
        setContentView(signOutBinding.getRoot());
        user = new User();
        user.setUsername(Auth.firebaseAuth.getCurrentUser().getEmail());
        user.setPassword(Auth.firebaseAuth.getCurrentUser().getUid());
        signOutBinding.setUser(user);
        setOnClick();
    }

    private void setOnClick(){
        signOutBinding.signOutButton.setOnClickListener(v->{
            Auth.firebaseAuth.signOut();
            user = new User();
        });
    }
}