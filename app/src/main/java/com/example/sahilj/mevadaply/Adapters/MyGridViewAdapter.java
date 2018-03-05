package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.DesignDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class MyGridViewAdapter extends ArrayAdapter<DesignDetails> {

        private Context mContext;
        private int layoutResourceId;
        private List<DesignDetails> mGridData;

        public MyGridViewAdapter(Context mContext, int layoutResourceId, List<DesignDetails> mGridData) {
            super(mContext, layoutResourceId, mGridData);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;   
            this.mGridData = mGridData;
        }

        /**
         * Updates grid data and refresh grid items.
         * @param mGridData
         */
        public void setGridData(List<DesignDetails> mGridData) {
            this.mGridData = mGridData;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;
            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            DesignDetails item = mGridData.get(position);

            Glide.with(mContext).load(item.getPhoto_url()).into(holder.imageView);
            return row;
        }

        static class ViewHolder {
            ImageView imageView;
        }
}
