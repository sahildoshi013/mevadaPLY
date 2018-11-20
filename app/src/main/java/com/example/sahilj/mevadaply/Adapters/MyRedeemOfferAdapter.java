package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.recyclerview.widget.RecyclerView;
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Log.v(TAG,"Position " + position);
        final boolean isAvailable;

        final OfferDetail detail = data.get(position);
        String url = detail.getOfferImage();
        if(!url.equals(MyConstants.NULL_URL))
            Glide.with(activity).load(detail.getOfferImage()).into(holder.imgOfferPic).onLoadStarted(activity.getResources().getDrawable(R.drawable.ic_placeholder));
        holder.tvOfferName.setText(detail.getOfferName());
        holder.tvOfferDesc.setText(detail.getOfferDescription());
        holder.tvOfferPoint.setText(String.valueOf(detail.getOfferPoint()));

        int point = detail.getOfferPoint();

        if(MyUtilities.getSum()-point<0) {
            String pointNeed = point - MyUtilities.getSum() + " needed";
            holder.tvPointNeeded.setText(pointNeed);
            isAvailable = false;
        }else{
            isAvailable = true;
            holder.tvPointNeeded.setVisibility(View.GONE);
        }

        holder.btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: btnredeem clicked");
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

                tvText.setText(detail.getOfferName());
                tvDesc.setText(detail.getOfferDescription());
                tvPoint.setText(String.valueOf(detail.getOfferPoint()));
                if(detail.getOfferImage().compareTo(MyConstants.NULL_URL)!=0)
                    Glide.with(activity).load(detail.getOfferImage()).into(imageView).onLoadStarted(activity.getResources().getDrawable(R.drawable.ic_placeholder));

                deleteDialogView.findViewById(R.id.btnRoCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialogView.findViewById(R.id.btnRoRedeem).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "onClick: "  + detail.getOfferName() + detail.getOfferId());
                        sendRequest(detail.getOfferId());
                        v.setEnabled(false);
                    }
                });

                if(!isAvailable) {
                    deleteDialogView.findViewById(R.id.btnRoRedeem).setEnabled(false);
                    Log.i(TAG, "onClick: false");
                }else {
                    deleteDialogView.findViewById(R.id.btnRoRedeem).setEnabled(true);
                    Log.i(TAG, "onClick: true");
                }
                deleteDialog.show();
            }
        });

    }

    private void sendRequest(final Integer redeem_offer_id) {
        alertDialog = MyConstants.alertBox(activity);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.onBackPressed();
            }
        });
        alertDialog.setTitle("Fail");
        alertDialog.setTitle("Something went wrong! Please try later");

        Call<InsertResult> call = MyConstants.apiInterface.redeemPoints(redeem_offer_id,MyConstants.USER_DETAILS.getUserId());

        call.enqueue(new Callback<InsertResult>() {
            @Override
            public void onResponse(Call<InsertResult> call, Response<InsertResult> response) {
                deleteDialog.dismiss();
                Log.i(TAG, "onResponse: " + response.code());
                if(response.code()==200 && response.body().isStatus()) {
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("You'll Shortly Contacted via Call.");
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                    MyConstants.USER_DETAILS = response.body().getData();
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
