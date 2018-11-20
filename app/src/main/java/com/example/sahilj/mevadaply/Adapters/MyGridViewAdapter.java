package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.DisplayImageFragment;
import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.DesignDetails;
import com.example.sahilj.mevadaply.Utils.MyConstants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class MyGridViewAdapter extends ArrayAdapter<DesignDetails> {

    private final FragmentManager fragmentManager;
    private Context mContext;
        private int layoutResourceId;
        private List<DesignDetails> mGridData;

        public MyGridViewAdapter(Context mContext, int layoutResourceId, List<DesignDetails> mGridData, FragmentManager fragmentManager) {
            super(mContext, layoutResourceId, mGridData);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;   
            this.mGridData = mGridData;
            this.fragmentManager=fragmentManager;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;
            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.imageView = row.findViewById(R.id.grid_item_image);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            final DesignDetails item = mGridData.get(position);

            Glide.with(mContext).load(item.getDesignImage()).into(holder.imageView).onLoadStarted(mContext.getResources().getDrawable(R.drawable.ic_placeholder));

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DisplayImageFragment displayImageFragment = new DisplayImageFragment();
                    Bundle bundle=new Bundle();
                    bundle.putInt(MyConstants.CURRENT_SELECTED_ID,position);
                    bundle.putSerializable(MyConstants.DATA, (Serializable) mGridData);
                    displayImageFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().add(R.id.frmContainer,displayImageFragment).addToBackStack(null).commit();
                }
            });
            return row;
        }

        static class ViewHolder {
            ImageView imageView;
        }
}
