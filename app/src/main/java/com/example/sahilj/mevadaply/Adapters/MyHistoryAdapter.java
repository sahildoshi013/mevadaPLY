package com.example.sahilj.mevadaply.Adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.v(TAG,"Position " + position);
        TransDetails detail = data.get(position);
        holder.tvDate.setText(detail.getTrans_time());
        holder.tvDesc.setText(detail.getTrans_comment());
        holder.tvType.setText(detail.getTrans_type());
        holder.tvPoints.setText(String.valueOf(detail.getTrans_amount()));
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

        MyViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvType = itemView.findViewById(R.id.tvType);
            tvPoints = itemView.findViewById(R.id.tvPoints);

        }
    }
}
