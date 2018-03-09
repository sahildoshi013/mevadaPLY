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

import com.example.sahilj.mevadaply.Adapters.MyHistoryAdapter;
import com.example.sahilj.mevadaply.Adapters.MyRedeemOfferAdapter;
import com.example.sahilj.mevadaply.Responses.OfferDetail;
import com.example.sahilj.mevadaply.Responses.RedeemOfferResult;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class RedeemOfferFragment extends Fragment {


    private static final String TAG = "Redeem Fragment";
    private RecyclerView rvRedeemOffer;

    public RedeemOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem_offer, container, false);

        rvRedeemOffer=view.findViewById(R.id.rvRedeemOffer);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvRedeemOffer.setLayoutManager(mLayoutManager);

        getRedeemOffer();
        return view;
    }

    private void getRedeemOffer() {
        Call<RedeemOfferResult> call=apiInterface.getRedeemOffers();
        call.enqueue(new Callback<RedeemOfferResult>() {
            @Override
            public void onResponse(Call<RedeemOfferResult> call, Response<RedeemOfferResult> response) {
                if(response.body().isStatus()){
                    List<OfferDetail> data = response.body().getData();
                    MyRedeemOfferAdapter adapter = new MyRedeemOfferAdapter(data, getActivity());
                    rvRedeemOffer.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<RedeemOfferResult> call, Throwable t) {
                Log.v(TAG,"Fail To Load Data",t);
            }
        });
    }

}
