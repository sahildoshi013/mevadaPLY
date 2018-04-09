package com.example.sahilj.mevadaply;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.Responses.InsertResult;
import com.example.sahilj.mevadaply.Responses.Result;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Responses.UserDetails;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;

public class Welcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Welcome";
    private static final int RC_SIGN_IN = 1;
    private CircularImageView profilePic;
    private TextView userName;
    private TextView userPoints;
    private TextView userAddress;
    private NavigationView navigationView;
    private TextView drawerNumber;
    private TextView drawerName;
    private CircularImageView drawerProfilePic;
    private AlertDialog alertDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                setShareIntent();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        alertDialog = MyConstants.alertBox(Welcome.this);
        auth= FirebaseAuth.getInstance();

        initialisingVariable();
        setUserDetails();

        //Open Profile Update Activity for new User
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            if(bundle.getInt(MyConstants.TIME, 1) != 1) {
                Intent openProfile = new Intent(getBaseContext(), ContainerActivity.class);
                openProfile.putExtra(MyConstants.TIME, 1);
                openProfile.putExtra(MyConstants.TYPE, MyConstants.TYPE_PROFILE);
                startActivity(openProfile);
            }

            if(bundle.get(MyConstants.POINTS)!=null){
                String points = "Total : " + bundle.getInt(MyConstants.POINTS);
                userPoints.setText(points);
            }
        }
    }

    private void setUserDetails() {
        if(MyConstants.USER_DETAILS!=null) {
            UserDetails details = MyConstants.USER_DETAILS;
            String name = details.getUser_fname() + " " + details.getUser_lname();
            String area = details.get_area();

            Log.v(TAG,"url : "+details.getUser_pic_url());
            if(!details.getUser_pic_url().equals(MyConstants.NULL_URL)) {
                Glide.with(getApplicationContext()).load(details.getUser_pic_url()).into(drawerProfilePic);
                Glide.with(getApplicationContext()).load(details.getUser_pic_url()).into(profilePic);
            }else{
                drawerProfilePic.setImageResource(R.drawable.ic_person_black_24dp);
                profilePic.setImageResource(R.drawable.ic_person_black_24dp);
            }
            drawerName.setText(name);
            drawerNumber.setText(details.getUser_phone());
            userName.setText(name);
            userAddress.setText(area);
        }
    }

    private void initialisingVariable() {
        profilePic = findViewById(R.id.imgUserProfile);
        userName = findViewById(R.id.tvName);
        userPoints = findViewById(R.id.tvPoints);
        userAddress = findViewById(R.id.tvArea);
        View view=navigationView.getHeaderView(0);
        drawerProfilePic = view.findViewById(R.id.imgDrawerProfilePic);
        drawerName = view.findViewById(R.id.tvDrawerUserName);
        drawerNumber = view.findViewById(R.id.tvDrawerUserNumber);

        userPoints.setText(MyConstants.DEFAULT_POINTS);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Call to update the share intent
    private void setShareIntent() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Mevada PLY");
            String sAux = "\nLet me recommend you Mevada PLY application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
            intent.putExtra("time",0);
            intent.putExtra(MyConstants.TYPE,MyConstants.TYPE_PROFILE);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
            intent.putExtra("time",0);
            intent.putExtra(MyConstants.TYPE,MyConstants.TYPE_HISTORY);
            startActivity(intent);
        } else if (id == R.id.nav_changeNumber) {
            changeNumber();
        }
        /*else if (id == R.id.nav_manage) {

        } */else if (id == R.id.nav_share) {
            setShareIntent();
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            MyUtilities.setSum();
            Intent intent = new Intent(getBaseContext(),SplashScreen.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeNumber() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyConstants.SHAREDPRENAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MyConstants.OLD_NUMBER,MyUtilities.getPhoneNumber());
        editor.apply();

        //FirebaseAuth.getInstance().signOut();

        alertDialog.setTitle("Error !");
        alertDialog.setMessage("Something went wrong!\nlogin using This NUMBER : "+ MyUtilities.getPhoneNumber());
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
                startActivity(intent);
                finish();
            }
        });

        //FirebaseAuth.getInstance().signOut();

        AuthUI.IdpConfig.PhoneBuilder phoneBuilder = new AuthUI.IdpConfig.PhoneBuilder().setDefaultCountryIso("IN");

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                phoneBuilder.build()))
                        .build(),
                RC_SIGN_IN);
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

                SharedPreferences sharedPreferences = getSharedPreferences(MyConstants.SHAREDPRENAME,MODE_PRIVATE);
                sharedPreferences.edit().putString(MyConstants.NEW_NUMBER,MyUtilities.getPhoneNumber()).apply();
                String old_number = sharedPreferences.getString(MyConstants.OLD_NUMBER,null);
                String new_phone = MyUtilities.getPhoneNumber();
                if((old_number != null ? old_number.compareTo(new_phone) : 0) !=0)
                    updateNumber(sharedPreferences.getString(MyConstants.OLD_NUMBER,null),MyUtilities.getPhoneNumber());
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
                    //alertDialog.show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackbar(R.string.unknown_error);
                    Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    //alertDialog.show();
                    return;
                }
            }

            //showSnackbar(R.string.unknown_sign_in_response);
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNumber(String string, final String phoneNumber) {
        Log.v(TAG,"Old " + string + " new "+phoneNumber);

        Call<InsertResult> call = MyConstants.apiInterface.updateMobileNumber(string,phoneNumber);

        call.enqueue(new Callback<InsertResult>() {
            @Override
            public void onResponse(Call<InsertResult> call, Response<InsertResult> response) {
                if(response.body().isSuccess())
                {
                    SharedPreferences sharedPreferences = getSharedPreferences(MyConstants.SHAREDPRENAME,MODE_PRIVATE);
                    sharedPreferences.edit().putString(MyConstants.OLD_NUMBER,phoneNumber).apply();
                    Toast.makeText(Welcome.this, "Success", Toast.LENGTH_SHORT).show();
                    alertDialog.setTitle("Success !");
                    alertDialog.setMessage("Your Mobile Number is Updated.");
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                }
                alertDialog.show();
            }

            @Override
            public void onFailure(Call<InsertResult> call, Throwable t) {
                alertDialog.show();
            }
        });

    }

    public void showRedeemOffers(View view) {
        Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
        intent.putExtra("time",0);
        intent.putExtra(MyConstants.TYPE,MyConstants.TYPE_REDEEM);
        startActivity(intent);
    }

    public void showDesignTrends(View view) {
        Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
        intent.putExtra("time",0);
        intent.putExtra(MyConstants.TYPE,MyConstants.TYPE_DESIGN);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }

    public  void getUserData() {
        Call<Result> call=apiInterface.getUserData(MyUtilities.getPhoneNumber());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                getTransactionData();
                if(response.body().isStatus())
                {
                    MyConstants.USER_DETAILS = response.body().getDetails();
                    setUserDetails();
                }else{
                    Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
                    intent.putExtra(MyConstants.TIME,0);
                    intent.putExtra(MyConstants.TYPE,MyConstants.TYPE_PROFILE);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(Welcome.this, "No Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //get Transaction Data
    private void getTransactionData() {
        Log.v(TAG,"Start Getting MyUtilities");
        Call<TransResult> call=apiInterface.getTransactionData(MyUtilities.getPhoneNumber());
        call.enqueue(new Callback<TransResult>() {
            @Override
            public void onResponse(Call<TransResult> call, Response<TransResult> response) {
                if(response.body().isStatus()) {
                    userPoints.setText(String.valueOf(MyUtilities.getPointCount(response.body().getTransDetailsList())));
                }

            }

            @Override
            public void onFailure(Call<TransResult> call, Throwable t) {
                Log.v(TAG,"Fail Getting MyUtilities",t);
                Toast.makeText(Welcome.this, "No Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
