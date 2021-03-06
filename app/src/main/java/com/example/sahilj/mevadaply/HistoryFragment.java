package com.example.sahilj.mevadaply;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sahilj.mevadaply.Adapters.MyHistoryAdapter;
import com.example.sahilj.mevadaply.Responses.TransDetails;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    private static final String TAG = "History";
    private static List<TransDetails> data;
    private RecyclerView rvHistoryView;
    private MyHistoryAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        rvHistoryView = view.findViewById(R.id.rvHistoryView);

        getTransactionData();

        data = new ArrayList<>();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvHistoryView.setLayoutManager(mLayoutManager);

        return view;
    }

    private void getTransactionData() {
        Log.v(TAG,"Start Getting MyUtilities");
        Call<TransResult> call=apiInterface.getTransactionData(MyConstants.USER_DETAILS.getUserId());
        call.enqueue(new Callback<TransResult>() {
            @Override
            public void onResponse(Call<TransResult> call, Response<TransResult> response) {

                if(response.body().isStatus()){
                    data = response.body().getTransDetailsList();
                    adapter=new MyHistoryAdapter(data);
                    rvHistoryView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<TransResult> call, Throwable t) {
                Log.v(TAG,"Fail Getting MyUtilities",t);
            }
        });
    }

}
