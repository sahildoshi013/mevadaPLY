package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.sahilj.mevadaply.Responses.InsertResult;
import com.example.sahilj.mevadaply.Responses.OfferDetail;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sahil J on 3/1/2018.
 */

public class MyRedeemOfferAdapter extends RecyclerView.Adapter<MyRedeemOfferAdapter.MyViewHolder> {

    private static final String TAG = "Redeem Offer Adapter";
    private final List<OfferDetail> data;
    private final Activity activity;
    private AlertDialog deleteDialog;
    private AlertDialog alertDialog;
    private boolean isAvailable;

    public MyRedeemOfferAdapter(List<OfferDetail> data, Activity activity) {
        this.data=data;
        this.activity=activity;
        this.isAvailable = true;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_redeem_offer,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Log.v(TAG,"Position " + position);
        final OfferDetail detail = data.get(position);
        String url = detail.getUrl();
        if(!url.equals(MyConstants.NULL_URL))
            Glide.with(activity).load(detail.getUrl()).into(holder.imgOfferPic).onLoadStarted(activity.getResources().getDrawable(R.drawable.ic_placeholder));
        holder.tvOfferName.setText(detail.getRedeem_offer_name());
        holder.tvOfferDesc.setText(detail.getRedeem_offer_description());
        holder.tvOfferPoint.setText(String.valueOf(detail.getRedeem_offer_points()));

        int point = detail.getRedeem_offer_points();

        if(MyUtilities.getSum()-point<0) {
            String pointNeed = point-MyUtilities.getSum() + " needed";
            holder.tvPointNeeded.setText(pointNeed);
            isAvailable = false;
        }else{
            holder.tvPointNeeded.setVisibility(View.GONE);
        }

        holder.btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(activity);
                final View deleteDialogView = factory.inflate(R.layout.content_redeem_offer_detail, null);
                deleteDialog = new AlertDialog.Builder(activity).create();
                deleteDialog.setView(deleteDialogView);
                TextView tvText = deleteDialogView.findViewById(R.id.tvRoTitle);
                CircularImageView imageView = deleteDialogView.findViewById(R.id.roImg);
                TextView tvDesc = deleteDialogView.findViewById(R.id.tvRoDesc);
                TextView tvPoint = deleteDialogView.findViewById(R.id.tvRoPoints);

                deleteDialogView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
                deleteDialog.setCancelable(false);

                tvText.setText(detail.getRedeem_offer_name());
                tvDesc.setText(detail.getRedeem_offer_description());
                tvPoint.setText(String.valueOf(detail.getRedeem_offer_points()));
                if(detail.getUrl().compareTo(MyConstants.NULL_URL)!=0)
                    Glide.with(activity).load(detail.getUrl()).into(imageView).onLoadStarted(activity.getResources().getDrawable(R.drawable.ic_placeholder));

                deleteDialogView.findViewById(R.id.btnRoCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialogView.findViewById(R.id.btnRoRedeem).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        sendRequest(detail.getRedeem_offer_id());
                    }
                });

                if(!isAvailable)
                    deleteDialogView.findViewById(R.id.btnRoRedeem).setEnabled(false);
                deleteDialog.show();
            }
        });

    }

    private void sendRequest(int redeem_offer_id) {
        alertDialog = MyConstants.alertBox(activity);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.onBackPressed();
            }
        });
        alertDialog.setTitle("Fail");
        alertDialog.setTitle("Something went wrong! Please try later");

        Call<InsertResult> call = MyConstants.apiInterface.redeemPoints(redeem_offer_id,MyUtilities.getPhoneNumber());

        call.enqueue(new Callback<InsertResult>() {
            @Override
            public void onResponse(Call<InsertResult> call, Response<InsertResult> response) {
                deleteDialog.dismiss();
                if(response.body().isSuccess()) {
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("You'll Shortly Contacted via Call.");
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(activity, "Fail to Proceed!", Toast.LENGTH_SHORT).show();
                }
                alertDialog.show();
            }

            @Override
            public void onFailure(Call<InsertResult> call, Throwable t) {
                deleteDialog.dismiss();
                alertDialog.show();
                Log.v(TAG,"Fail "+t.getMessage(),t);
                Toast.makeText(activity, "Fail to Proceed!", Toast.LENGTH_SHORT).show();
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
        private final TextView tvPointNeeded;

        MyViewHolder(View itemView) {
            super(itemView);
            imgOfferPic = itemView.findViewById(R.id.imgOfferPic);
            tvOfferName = itemView.findViewById(R.id.tvOfferName);
            tvOfferDesc = itemView.findViewById(R.id.tvOfferDesc);
            tvOfferPoint = itemView.findViewById(R.id.tvOfferPoints);
            tvPointNeeded = itemView.findViewById(R.id.tvPointNeeded);
            btnRedeem = itemView.findViewById(R.id.btnRedeem);
        }
    }
}
