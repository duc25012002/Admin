package com.hdcompany.admin;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hdcompany.admin.activity.PrimeActivity;
import com.hdcompany.admin.activity.RegisterActivity;
import com.hdcompany.admin.databinding.LoginScreenBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.User;
import com.hdcompany.admin.utility.Utility;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private LoginScreenBinding loginScreenBinding;
    private User user;
    private FirebaseUser firebaseUser = Auth.firebaseAuth().getCurrentUser();

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = Auth.firebaseAuth().getCurrentUser();
        if (firebaseUser != null) {
            forwardPrime();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginScreenBinding = LoginScreenBinding.inflate(getLayoutInflater());
        setContentView(loginScreenBinding.getRoot());
        initUserData();
        setOnClick();
    }
    public void onGoogleSignIn(){
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("TAGBECHJ", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d("TAGBECHJ", e.getLocalizedMessage());
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String email = credential.getId();
                    String password = credential.getPassword();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d("TAGBECHJ", "Got ID token.");

                        Auth.firebaseAuth().signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = null;
                                            do{
                                                user = Auth.firebaseAuth().getCurrentUser();
                                            }while (user == null);
                                            HashMap<String,String> map = new HashMap<>();
                                            map.put("id", user.getUid());
                                            Auth.sportClothsDatabase(MainActivity.this).getReference().child("users").child(user.getUid()).setValue(map);
                                            // Proceed with your application logic after successful authentication
                                            forwardPrime();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("TAGBECHJ", "signInWithCredential:failure", task.getException());
                                            // Handle the failure. You can display an error message to the user or retry the authentication process.
                                        }
                                    }
                                });
                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d("TAGBECHJ", "Got password.");
                    }
                } catch (ApiException e) {
                    // ...
                    switch (e.getStatusCode()) {
                        case CommonStatusCodes.CANCELED:
                            Log.d("TAGBECHJ", "One-tap dialog was closed.");
                            // Don't re-prompt the user.
                            showOneTapUI = false;
                            break;
                        case CommonStatusCodes.NETWORK_ERROR:
                            Log.d("TAGBECHJ", "One-tap encountered a network error.");
                            // Try again or just ignore.
                            break;
                        default:
                            Log.d("TAGBECHJ", "Couldn't get credential from result."
                                    + e.getLocalizedMessage());
                            break;
                    }
                }
                break;
        }

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
            Utility.hideSoftKeyboard(this);
            try {
                if (!user.getUsername().equals("") && !user.getPassword().equals("")) {
                    firebaseUser = Auth.loginAuth(this, user);
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
        GOOGLE LOGIN
         */
        loginScreenBinding.googleLoginClick.setOnClickListener(v -> {
//            signInRequest = BeginSignInRequest.builder()
//                    .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                            .setSupported(true)
//                            // Your server's client ID, not your Android client ID.
//                            .setServerClientId(getString(R.string.default_web_client_id))
//                            // Only show accounts previously used to sign in.
//                            .setFilterByAuthorizedAccounts(true)
//                            .build())
//                    .build();
            onGoogleSignIn();
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
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
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
    FORWARD TO SIGN UP
     */
    private void forwardToSignUp() {
        Intent i = new Intent(this, RegisterActivity.class);
        this.startActivity(i);
        this.finish();
    }

    /*
    INIT BINDING VIEW DATA
     */
    private void initUserData() {
        this.user = new User();
        System.out.println(this.user.toString());
        /*
        Binding User
         */
        loginScreenBinding.setUser(user);
    }
}