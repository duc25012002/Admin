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

public class RegisterActivity extends AppCompatActivity {
    private GoogleSignInClient client;
    private static final int RC_SIGN_IN = 20;
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
        // Get Client
        client = GoogleSignIn.getClient(this, Auth.gso);
    }

     private void setOnClick() {

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
        registerBinding.signupButton.setOnClickListener(v -> {
            try {
                if(
                        !user.getUsername().equals("") && !user.getPassword().equals("")
                ){
                    firebaseUser = Auth.loginAuth(this,user);
                    firebaseUser = Auth.firebaseAuth().getCurrentUser();
                }else{
                    Toast.makeText(this, "Please don't leave any empty information!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        registerBinding.googleLoginClick.setOnClickListener(v -> {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        forwardScreen();
    }


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
                        Toast.makeText(RegisterActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takeLoginAction() {
        Intent i = new Intent(this, SignOutActivity.class);
        this.startActivity(i);
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