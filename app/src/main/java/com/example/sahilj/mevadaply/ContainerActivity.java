package com.example.sahilj.mevadaply;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;

public class ContainerActivity extends AppCompatActivity {

    private FrameLayout frmContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantainer);

        frmContainer = findViewById(R.id.frmContainer);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_PROFILE))
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),new UserProfileFragment(this,bundle)).commit();
            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_HISTORY))
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),new HistoryFragment(this)).commit();
            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_REDEEM))
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),new RedeemOfferFragment(this)).commit();
            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_DESIGN))
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),new DesignTrendsFragment(this)).addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }
}
