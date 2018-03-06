package com.example.sahilj.mevadaply;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sahilj.mevadaply.Responses.Result;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Responses.UserDetails;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUserMetadata;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;

public class SplashScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "Splash Screen";
    private FirebaseAuth auth;
    private Intent openWelcome;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLogin();

    }

    private void checkLogin() {
        openWelcome=new Intent(getBaseContext(),Welcome.class);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            getUserData();
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

    //Get User Data
    public  void getUserData() {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(SplashScreen.this)
                .setTitle("Error !")
                .setMessage("No Internet")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        alertDialog = alertBuilder.create();
        Call<Result> call=apiInterface.getUserData(MyUtilities.getPhoneNumber());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.body().isStatus())
                {
                    MyConstants.USER_DETAILS = response.body().getDetails();
                    getTransactionData(response.body().getDetails());
                }else{
                    Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
                    intent.putExtra(MyConstants.TIME,0);
                    intent.putExtra(MyConstants.TYPE,"profile");
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                alertDialog.show();
            }
        });
    }

    //get Transaction Data
    private void getTransactionData(final UserDetails details) {
        Log.v(TAG,"Start Getting MyUtilities");
        Call<TransResult> call=apiInterface.getTransactionData(MyUtilities.getPhoneNumber());
        call.enqueue(new Callback<TransResult>() {
            @Override
            public void onResponse(Call<TransResult> call, Response<TransResult> response) {
                if(response.body().isStatus()) {
                    openWelcome.getIntExtra(MyConstants.POINTS,MyUtilities.getPointCount(response.body().getTransDetailsList()));
                    openWelcome.putExtra(MyConstants.TIME,1);
                    startActivity(openWelcome);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TransResult> call, Throwable t) {
                Log.v(TAG,"Fail Getting Transaction",t);
                alertDialog.show();
            }
        });
    }

}
