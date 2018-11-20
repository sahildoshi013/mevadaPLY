package com.example.sahilj.mevadaply;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sahilj.mevadaply.Responses.InsertResult;
import com.example.sahilj.mevadaply.Responses.UserResult;
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
    private static final int PROFILE = 1;
    private FirebaseAuth auth;
    private Intent openWelcome;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertDialog = MyConstants.alertBox(SplashScreen.this);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        checkMobileNumber();

    }

    private void checkMobileNumber() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyConstants.SHAREDPRENAME,MODE_PRIVATE);
        String oldNumber = sharedPreferences.getString(MyConstants.OLD_NUMBER,null);
        String newNumber = sharedPreferences.getString(MyConstants.NEW_NUMBER,null);

        if(oldNumber!=null && newNumber!=null && oldNumber.compareTo(newNumber)!=0)
            updateNumber(oldNumber,newNumber);
        else
            checkLogin();
    }

    private void updateNumber(String string, final String phoneNumber) {
        Log.v(TAG,"Old " + string + " new "+phoneNumber);

        alertDialog.setTitle("Error!");
        alertDialog.setMessage("Something went wrong!");

        Call<InsertResult> call = MyConstants.apiInterface.updateMobileNumber(string,phoneNumber);
        call.enqueue(new Callback<InsertResult>() {
            @Override
            public void onResponse(Call<InsertResult> call, Response<InsertResult> response) {
                if(response.body().isStatus())
                {
                    SharedPreferences sharedPreferences = getSharedPreferences(MyConstants.SHAREDPRENAME,MODE_PRIVATE);
                    sharedPreferences.edit().putString(MyConstants.OLD_NUMBER,phoneNumber).apply();
                    getUserData();
                }else
                    alertDialog.show();
            }

            @Override
            public void onFailure(Call<InsertResult> call, Throwable t) {
                alertDialog.show();
            }
        });

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
                    //Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //showSnackbar(R.string.no_internet_connection);
                    Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                    alertDialog.show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackbar(R.string.unknown_error);
                    Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    alertDialog.show();
                    return;
                }
            }

            //showSnackbar(R.string.unknown_sign_in_response);
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
        if(resultCode!=RESULT_OK && requestCode==PROFILE){
            finish();
        }
    }

    //Get User Data
    public  void getUserData() {
        Call<UserResult> call=apiInterface.getUserData(MyUtilities.getPhoneNumber());
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                if(response.body().isStatus())
                {
                    MyConstants.USER_DETAILS = response.body().getDetails();
                    getTransactionData(response.body().getDetails());
                }else{
                    Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
                    intent.putExtra(MyConstants.TIME,0);
                    intent.putExtra(MyConstants.TYPE,"profile");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Log.v(TAG,"Message : "+t.getMessage() ,t);
                Toast.makeText(SplashScreen.this, "Fail User", Toast.LENGTH_SHORT).show();
                alertDialog.show();
            }
        });
    }

    //get Transaction Data
    private void getTransactionData(final UserDetails details) {
        Log.v(TAG,"Start Getting MyUtilities");
        Call<TransResult> call=apiInterface.getTransactionData(details.getUserId());
        call.enqueue(new Callback<TransResult>() {
            @Override
            public void onResponse(Call<TransResult> call, Response<TransResult> response) {
                if(response.body().isStatus()) {
                    openWelcome.putExtra(MyConstants.POINTS,MyUtilities.getPointCount(response.body().getTransDetailsList()));
                }else{
                    openWelcome.putExtra(MyConstants.POINTS,0);
                }
                openWelcome.putExtra(MyConstants.TIME,1);
                startActivity(openWelcome);
                finish();
            }

            @Override
            public void onFailure(Call<TransResult> call, Throwable t) {
                Log.v(TAG,"Fail Getting Transaction",t);
                Toast.makeText(SplashScreen.this, "Fail tr", Toast.LENGTH_SHORT).show();
                alertDialog.show();
            }
        });
    }

}
