package com.example.sahilj.mevadaply;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUserMetadata;

import java.util.Arrays;

public class SplashScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final long SPLASH_TIME_OUT = 3000;
    private FirebaseAuth auth;
    private Intent openWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseAuth.getInstance().signOut();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                checkLogin();
            }
        }, SPLASH_TIME_OUT);
    }

    private void checkLogin() {
        openWelcome=new Intent(getBaseContext(),Welcome.class);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in

            openWelcome.putExtra("time",1);
            startActivity(openWelcome);
            finish();
        } else {
            // not signed in

            AuthUI.IdpConfig.PhoneBuilder phoneBuilder = new AuthUI.IdpConfig.PhoneBuilder().setDefaultCountryIso("IN");

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    phoneBuilder.build()))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //startActivity(SignedInActivity.createIntent(this, response));
                //finish();
                FirebaseUserMetadata metadata = auth.getCurrentUser().getMetadata();
                if (metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {
                    // The user is new, show them a fancy intro screen!

                    Toast.makeText(this, "First Time User", Toast.LENGTH_SHORT).show();
                    openWelcome.putExtra("time",2);
                    startActivity(openWelcome);
                    finish();
                } else {
                    // This is an existing user, show them a welcome back screen.
                    openWelcome.putExtra("time",1);
                    Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
                    startActivity(openWelcome);
                    finish();
                }

                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    //showSnackbar(R.string.sign_in_cancelled);
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //showSnackbar(R.string.no_internet_connection);
                    Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackbar(R.string.unknown_error);
                    Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            //showSnackbar(R.string.unknown_sign_in_response);
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }
}
