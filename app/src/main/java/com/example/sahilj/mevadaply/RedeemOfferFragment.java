package com.example.sahilj.mevadaply;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahilj.mevadaply.Adapters.MyRedeemOfferAdapter;
import com.example.sahilj.mevadaply.Responses.OfferDetail;
import com.example.sahilj.mevadaply.Responses.RedeemOfferResult;
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
    private TextView tvPoint;

    public RedeemOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem_offer, container, false);

        rvRedeemOffer=view.findViewById(R.id.rvRedeemOffer);
        tvPoint = view.findViewById(R.id.tvRedeemPoint);

        String point = "Points : " + MyUtilities.getSum();
        tvPoint.setText(point);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvRedeemOffer.setLayoutManager(mLayoutManager);

        getRedeemOffer();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String point = "Points : " + MyUtilities.getSum();
        tvPoint.setText(point);
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
