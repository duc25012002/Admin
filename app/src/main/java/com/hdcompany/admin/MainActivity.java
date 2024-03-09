package com.hdcompany.admin;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hdcompany.admin.activity.RegisterActivity;
import com.hdcompany.admin.activity.SignOutActivity;
import com.hdcompany.admin.databinding.LoginScreenBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.User;

import io.grpc.TlsServerCredentials;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient client;
    private static final int RC_SIGN_IN = 20;
    private LoginScreenBinding loginScreenBinding;
    private User user;

    private FirebaseUser firebaseUser = Auth.firebaseAuth().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginScreenBinding = LoginScreenBinding.inflate(getLayoutInflater());
        setContentView(loginScreenBinding.getRoot());
        initUserData();
        setOnClick();
        // Get Client
        client = GoogleSignIn.getClient(this, Auth.gso);
    }

      private void setOnClick() {

        /*
        Set Click Event SIGN UP
         */
        loginScreenBinding.signUpClickTitle.setOnClickListener(v -> {
            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        forwardToSignUp();
                    }
                }, 600);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        /*
        Button forgot
         */
        loginScreenBinding.forgotPasswordClickTitle.setOnClickListener(v -> {
            Toast.makeText(this, "Soon", Toast.LENGTH_SHORT).show();
        });

        /*
        Login Button
         */
        loginScreenBinding.loginButton.setOnClickListener(v -> {
          try{
              if(
                      !user.getUsername().equals("") && !user.getPassword().equals("")
              ){
                  firebaseUser = Auth.loginAuth(this,user);
                  firebaseUser = Auth.firebaseAuth().getCurrentUser();
                  forwardScreen();
              }else{
                  Toast.makeText(this, "Please don't leave any empty information!", Toast.LENGTH_SHORT).show();
              }
          }catch (Exception e){
              e.printStackTrace();
          }
        });
        /*
        GOOGLE LOGIN
         */
        loginScreenBinding.googleLoginClick.setOnClickListener(v -> {
            googleSignIn();
        });
    }

     private void googleSignIn() {
        Intent i = client.getSignInIntent();
        this.startActivityForResult(i, RC_SIGN_IN);
    }

    @Override
    synchronized protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseUser = Auth.firebaseAuthen(account.getIdToken());
                firebaseUser = Auth.firebaseAuth().getCurrentUser();
                /*
                Forward to the next screen. Logout
                */
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // End the task, and got the user
        forwardScreen();
    }

    /*
    FORWARD TO LOGOUT
     */
     private void forwardScreen() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        String email = firebaseUser.getEmail();
                        for(int i=0;i<10;i++){
                            System.out.println("LOGIN EMAIL FROM FIREBASE USER : " + email );
                        }
                        takeLoginAction();
                    }catch (Exception e){
                       for(int i=0;i<10;i++){
                           System.out.println("LOGIN ERROR");
                       }
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                    }
            }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    FORWARD TO LOGOUT
     */
    private void takeLoginAction() {
        Intent i = new Intent(this, SignOutActivity.class);
        this.startActivity(i);
        this.finish();
    }

    private void forwardToSignUp() {
        Intent i = new Intent(this, RegisterActivity.class);
        this.startActivity(i);
        this.finish();
    }

    private void initUserData() {
        this.user = new User();
        System.out.println(this.user.toString());
        /*
        Binding User
         */
        loginScreenBinding.setUser(user);
    }


}