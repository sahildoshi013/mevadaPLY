package com.example.sahilj.mevadaply;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.sahilj.mevadaply.Utils.MyConstants;

public class ContainerActivity extends AppCompatActivity {

    private FrameLayout frmContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantainer);

        frmContainer = findViewById(R.id.frmContainer);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){

            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_PROFILE)) {
                UserProfileFragment userProfileFragment = new UserProfileFragment();
                userProfileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),userProfileFragment).commit();
            }
            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_HISTORY)) {
                HistoryFragment historyFragment=new HistoryFragment();
                historyFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),historyFragment).commit();
            }
            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_REDEEM)) {
                RedeemOfferFragment redeemOfferFragment=new RedeemOfferFragment();
                redeemOfferFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(), redeemOfferFragment).commit();
            }
            if(bundle.get(MyConstants.TYPE).equals(MyConstants.TYPE_DESIGN)) {
                DesignTrendsFragment designTrendsFragment=new DesignTrendsFragment();
                designTrendsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),designTrendsFragment).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getFragments().toString().contains("UserProfileFragment")) {
            setResult(RESULT_CANCELED);
            finish();
        }
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
