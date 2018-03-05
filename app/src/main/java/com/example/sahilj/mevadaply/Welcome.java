package com.example.sahilj.mevadaply;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.Responses.Result;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;

public class Welcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Welcome";
    private CircularImageView profilePic;
    private TextView userName;
    private TextView userPoints;
    private TextView userAddress;
    private NavigationView navigationView;
    private TextView drawerNumber;
    private TextView drawerName;
    private CircularImageView drawerProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Open Profile Update Activity for new User
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.getInt("time", 1) != 1) {
            Toast.makeText(this, "Welcome New User", Toast.LENGTH_SHORT).show();
            Intent openProfile = new Intent(getBaseContext(),ContainerActivity.class);
            openProfile.putExtra("time",1);
            openProfile.putExtra(MyConstants.TYPE, MyConstants.TYPE_PROFILE);
            startActivity(openProfile);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initialisingVariable();
        getUserData();
        getTransactionData();


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getBaseContext(),SplashScreen.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        getTransactionData();
    }

    //Get User Data
    public  void getUserData() {

        Call<Result> call=apiInterface.getUserData(MyUtilities.getPhoneNumber());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.body().isStatus())
                {
                    String name = response.body().getDetails().getUser_fname() + " " + response.body().getDetails().getUser_lname();
                    String area = response.body().getDetails().get_area();

                    Glide.with(getApplicationContext()).load(response.body().getDetails().getUser_pic_url()).into(drawerProfilePic);
                    Glide.with(getApplicationContext()).load(response.body().getDetails().getUser_pic_url()).into(profilePic);
                    drawerName.setText(name);
                    drawerNumber.setText(response.body().getDetails().getUser_phone());
                    userName.setText(name);
                    userAddress.setText(area);
                }else{
                    Intent intent = new Intent(getBaseContext(),ContainerActivity.class);
                    intent.putExtra("time",0);
                    intent.putExtra("type","profile");
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

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
                String points = "Total : 0";
                if(response.body().isStatus()) {
                    points = "Total : " + MyUtilities.getPointCount(response.body().getTransDetailsList());
                }
                userPoints.setText(points);
            }

            @Override
            public void onFailure(Call<TransResult> call, Throwable t) {
                Log.v(TAG,"Fail Getting MyUtilities",t);
            }
        });
    }


}
