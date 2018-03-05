package com.example.sahilj.mevadaply;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sahilj.mevadaply.Adapters.MyDesignTrendsAdapter;
import com.example.sahilj.mevadaply.Responses.DesignTrendsDetail;
import com.example.sahilj.mevadaply.Responses.DesignTrendsResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class DesignTrendsFragment extends Fragment {


    private static final String TAG = "Design Trends";
    private RecyclerView rvDesignTrends;
    private Activity activity;

    public DesignTrendsFragment(Activity activity) {
        // Required empty public constructor
        this.activity=activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design_trends, container, false);

        rvDesignTrends=view.findViewById(R.id.rvDesignTrends);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        rvDesignTrends.setLayoutManager(mLayoutManager);

        getDesignTrends();

        return view;
    }

    private void getDesignTrends() {
        Call<DesignTrendsResult> call=apiInterface.getDesignTrends();
        call.enqueue(new Callback<DesignTrendsResult>() {
            @Override
            public void onResponse(Call<DesignTrendsResult> call, Response<DesignTrendsResult> response) {
                if(response.body().isStatus()){
                    List<DesignTrendsDetail> data = response.body().getData();
                    MyDesignTrendsAdapter adapter = new MyDesignTrendsAdapter(data, getActivity(),getFragmentManager());
                    rvDesignTrends.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DesignTrendsResult> call, Throwable t) {
                Log.v(TAG,"Fail To Load Data",t);
            }
        });
    }

}
