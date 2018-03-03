package com.example.sahilj.mevadaply;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sahilj.mevadaply.Responses.RedeemOfferResult;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Utils.MyUtilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class RedeemOfferFragment extends Fragment {


    private RecyclerView rvRedeemOffer;
    private Activity activity;

    public RedeemOfferFragment(Activity activity) {
        // Required empty public constructor
        this.activity=activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem_offer, container, false);
        rvRedeemOffer=view.findViewById(R.id.rvRedeemOffer);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
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

                }
            }

            @Override
            public void onFailure(Call<RedeemOfferResult> call, Throwable t) {

            }
        });
    }

}
