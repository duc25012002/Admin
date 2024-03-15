package com.hdcompany.admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.hdcompany.admin.MainActivity;
import com.hdcompany.admin.databinding.ActivityRegisterBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.User;
import com.hdcompany.admin.utility.Utility;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding registerBinding;
    private User user;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());
        initUserData();
        setOnClick();
    }

    private void setOnClick() {

        /*
            CLICK TO FORWARD TO LOGIN SCREEN
         */
        registerBinding.haveAnAccountClick.setOnClickListener(v -> {
            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        forwardToLogin();
                    }
                }, 600);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        /*
            CLICK TO SIGN UP
         */
        registerBinding.signupButton.setOnClickListener(v -> {
            Utility.hideSoftKeyboard(this);
            try {
                if (
                        !user.getUsername().equals("") && !user.getPassword().equals("")
                ) {
                    firebaseUser = Auth.signUpAuth(this, user);
                    firebaseUser = Auth.firebaseAuth().getCurrentUser();
                    forwardPrime();
                } else {
                    Toast.makeText(this, "Please don't leave any empty information!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        /*
            CLICK TO GOOGLE SIGN IN
         */
        registerBinding.googleLoginClick.setOnClickListener(v -> {
        });
    }

    private void forwardPrime(){
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        do {
                            firebaseUser = Auth.firebaseAuth().getCurrentUser();
                        } while (firebaseUser == null);
                        String email = firebaseUser.getEmail();
                        for (int i = 0; i < 10; i++) {
                            System.out.println("LOGIN EMAIL FROM FIREBASE USER : " + email);
                        }
                        takeLoginPrimeAction();
                    } catch (Exception e) {
                        for (int i = 0; i < 10; i++) {
                            System.out.println("LOGIN ERROR");
                        }
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takeLoginPrimeAction(){
        this.startActivity(new Intent(this, PrimeActivity.class));
        this.finish();
    }

    private void forwardToLogin() {
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
        this.finish();
    }

    private void initUserData() {
        this.user = new User();
        System.out.println("User: " + this.user.toString());
        registerBinding.setUser(user);

    }
}