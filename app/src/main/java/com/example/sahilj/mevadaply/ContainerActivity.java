package com.example.sahilj.mevadaply;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class ContainerActivity extends AppCompatActivity {

    private FrameLayout frmContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantainer);

        frmContainer = findViewById(R.id.frmContainer);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            if(bundle.get("type").equals("profile"))
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),new UserProfileFragment(this,bundle)).commit();
            if(bundle.get("type").equals("history"))
                getSupportFragmentManager().beginTransaction().replace(frmContainer.getId(),new HistoryFragment(this)).commit();
        }
    }
}
