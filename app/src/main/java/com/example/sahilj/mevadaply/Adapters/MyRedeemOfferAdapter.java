package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.OfferDetail;
import com.example.sahilj.mevadaply.Responses.TransDetails;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

/**
 * Created by Sahil J on 3/1/2018.
 */

public class MyRedeemOfferAdapter extends RecyclerView.Adapter<MyRedeemOfferAdapter.MyViewHolder> {

    private static final String TAG = "Redeem Offer Adapter";
    private final List<OfferDetail> data;
    private final Activity activity;

    public MyRedeemOfferAdapter(List<OfferDetail> data, Activity activity) {
        this.data=data;
        this.activity=activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_redeem_offer,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.v(TAG,"Position " + position);
        final OfferDetail detail = data.get(position);
        Glide.with(activity).load(detail.getUrl()).into(holder.imgOfferPic);
        holder.tvOfferName.setText(detail.getRedeem_offer_name());
        holder.tvOfferDesc.setText(detail.getRedeem_offer_description());
        holder.tvOfferPoint.setText(detail.getRedeem_offer_points());
        holder.btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "offer "+detail.getRedeem_offer_name(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final CircularImageView imgOfferPic;
        private final TextView tvOfferName;
        private final TextView tvOfferDesc;
        private final TextView tvOfferPoint;
        private final Button btnRedeem;

        MyViewHolder(View itemView) {
            super(itemView);
            imgOfferPic = itemView.findViewById(R.id.imgOfferPic);
            tvOfferName = itemView.findViewById(R.id.tvOfferName);
            tvOfferDesc = itemView.findViewById(R.id.tvOfferDesc);
            tvOfferPoint = itemView.findViewById(R.id.tvOfferPoint);
            btnRedeem = itemView.findViewById(R.id.btnRedeem);
        }
    }
}
