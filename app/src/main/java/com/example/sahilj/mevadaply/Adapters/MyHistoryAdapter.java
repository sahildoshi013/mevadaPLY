package com.example.sahilj.mevadaply.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.TransDetails;

import java.util.List;

/**
 * Created by Sahil J on 3/1/2018.
 */

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.MyViewHolder> {

    private static final String TAG = "History Adapter";
    private final List<TransDetails> data;

    public MyHistoryAdapter(List<TransDetails> data) {
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlistitem,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.v(TAG,"Position " + position);
        TransDetails detail = data.get(position);
        holder.tvDesc.setText(detail.getTrans_time());
        holder.tvDate.setText(detail.getTrans_comment());
        holder.tvType.setText(detail.getTrans_type());
        String type="";
        if(detail.getTrans_type().equals("Redeem")) {
            holder.ivTypeIcon.setImageResource(R.drawable.passbook_send_48dp);
            holder.tvPoints.setTextColor(Color.RED);
            type+="-";
        }
        else {
            holder.ivTypeIcon.setImageResource(R.drawable.passbook_recieve_12dp);
            holder.tvPoints.setTextColor(Color.GREEN);
            type+="+";
        }
        holder.tvPoints.setText(type+String.valueOf(detail.getTrans_amount()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvDesc;
        private TextView tvType;
        private TextView tvPoints;
        private ImageView ivTypeIcon;


        MyViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvType = itemView.findViewById(R.id.tvType);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            ivTypeIcon = itemView.findViewById(R.id.order_icon);

        }
    }
}
