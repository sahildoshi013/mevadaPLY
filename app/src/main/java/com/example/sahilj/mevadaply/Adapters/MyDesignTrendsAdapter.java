package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.DesignDetail;
import com.example.sahilj.mevadaply.Responses.DesignTrendsResult;
import com.example.sahilj.mevadaply.Responses.OfferDetail;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

/**
 * Created by Sahil J on 3/1/2018.
 */

public class MyDesignTrendsAdapter extends RecyclerView.Adapter<MyDesignTrendsAdapter.MyViewHolder> {

    private static final String TAG = "Redeem Offer Adapter";
    private final List<DesignDetail> data;
    private final Activity activity;

    public MyDesignTrendsAdapter(List<DesignDetail> data, Activity activity) {
        this.data=data;
        this.activity=activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_design_trends,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.v(TAG,"Position " + position);
        final DesignDetail detail = data.get(position);
        String url = detail.getUrl();
        if(!url.equals(MyConstants.NULL_URL))
            Glide.with(activity).load(detail.getUrl()).into(holder.imgDesignImage);
        holder.tvDesignName.setText(detail.getDesign_name());

        holder.cvDesignContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Design "+detail.getDesign_name(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvDesignName;
        private final CardView cvDesignContain;
        private final ImageView imgDesignImage;

        MyViewHolder(View itemView) {
            super(itemView);
            cvDesignContain = itemView.findViewById(R.id.cvDesignContain);
            tvDesignName = itemView.findViewById(R.id.tvDesignName);
            imgDesignImage = itemView.findViewById(R.id.imgDesignImage);
        }
    }
}
