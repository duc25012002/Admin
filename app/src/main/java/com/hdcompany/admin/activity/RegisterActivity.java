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
    private ActivityRegisterBinding registerBinding;
    private User user;
    private GoogleSignInClient client;
    private FirebaseUser firebaseUser;
    private static final int RC_SIGN_IN = 20;

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
            try {
                if (
                        !user.getUsername().equals("") && !user.getPassword().equals("")
                ) {
                    firebaseUser = Auth.loginAuth(this, user);
                    firebaseUser = Auth.firebaseAuth().getCurrentUser();
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
            googleSignIn();
        });
    }

    /*
        GOOGLE SIGN IN
     */
    private void googleSignIn() {
        Intent i = client.getSignInIntent();
        this.startActivityForResult(i, RC_SIGN_IN);
    }

    /*
        GOOGLE SIGN IN
     */
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
        forwardPrime();
    }


    /*
        FORWARD SCREEN TO SIGN OUT SCREEN
     */
    private void forwardScreen() {
        try {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        do {
                            firebaseUser = Auth.firebaseAuth().getCurrentUser();
                        }
                        while (firebaseUser == null);
                        String email = firebaseUser.getEmail();
                        for (int i = 0; i < 10; i++) {
                            System.out.println("LOGIN EMAIL FROM FIREBASE USER : " + email);
                        }
                        takeLoginAction();
                    } catch (Exception e) {
                        for (int i = 0; i < 10; i++) {
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
    }
    /*
        FORWARD TO SIGN OUT SCREEN
     */
    private void takeLoginAction() {
        Intent i = new Intent(this, SignOutActivity.class);
        this.startActivity(i);
        this.finish();
    }

    /*
        FORWARD TO LOGIN
     */
    private void forwardToLogin() {
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
        this.finish();
    }

    /*
        INIT USER BINDING IN VIEW
     */
    private void initUserData() {
        this.user = new User();
        System.out.println("User: " + this.user.toString());
        registerBinding.setUser(user);

    }
}