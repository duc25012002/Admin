package com.hdcompany.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

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
                user.setUsername(Auth.firebaseAuth().getCurrentUser().getEmail());
            }
        },1000);
    }

    private void setOnClick(){
        signOutBinding.signOutButton.setOnClickListener(v->{
            Toast.makeText(this, "App close in 2 seconds", Toast.LENGTH_SHORT).show();
            user.setUsername("Signed out");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Auth.firebaseAuth().signOut();
                    onFinish();
                }
            },2000);
        });
    }
    private void onFinish(){
        this.finish();
    }
}