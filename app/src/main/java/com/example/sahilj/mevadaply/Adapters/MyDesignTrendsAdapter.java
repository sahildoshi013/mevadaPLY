package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.DisplayDesignFragment;
import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.DesignTrendsDetail;
import com.example.sahilj.mevadaply.Utils.MyConstants;

import java.util.List;

/**
 * Created by Sahil J on 3/1/2018.
 */

public class MyDesignTrendsAdapter extends RecyclerView.Adapter<MyDesignTrendsAdapter.MyViewHolder> {

    private static final String TAG = "Redeem Offer Adapter";
    private final List<DesignTrendsDetail> data;
    private final Activity activity;
    private final FragmentManager fragmentManager;

    public MyDesignTrendsAdapter(List<DesignTrendsDetail> data, Activity activity, FragmentManager fragmentManager) {
        this.data=data;
        this.activity=activity;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_design_trends,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.v(TAG,"Position " + position);
        final DesignTrendsDetail detail = data.get(position);
//        String url = detail.getUrl();
//        if(!url.equals(MyConstants.NULL_URL))
//            Glide.with(activity).load(detail.getUrl()).into(holder.imgDesignImage).onLoadStarted(holder.context.getResources().getDrawable(R.drawable.ic_placeholder));
        holder.tvDesignName.setText(detail.getTypeName());

        holder.cvDesignContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayDesignFragment displayDesignFragment = new DisplayDesignFragment();
                Bundle bundle=new Bundle();
                bundle.putInt(MyConstants.CURRENT_SELECTED_ID,detail.getTypeId());
                displayDesignFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.frmContainer,displayDesignFragment).addToBackStack(null).commit();
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
        private Context context;

        MyViewHolder(View itemView) {
            super(itemView);
            cvDesignContain = itemView.findViewById(R.id.cvDesignContain);
            tvDesignName = itemView.findViewById(R.id.tvDesignName);
            imgDesignImage = itemView.findViewById(R.id.imgDesignImage);
            context=itemView.getContext();
        }
    }
}
