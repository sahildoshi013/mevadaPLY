package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.DisplayImageFragment;
import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.DesignDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class MyImageViewPagerAdapter extends PagerAdapter{


    private final List<DesignDetails> _imagePaths;
    private Activity _activity;
    private LayoutInflater inflater;

    // constructor
    public MyImageViewPagerAdapter(Activity activity, List<DesignDetails> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imgDisplay;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.content_display_image, container,
                false);

        imgDisplay = viewLayout.findViewById(R.id.imgDisplay);

        Glide.with(_activity).load(_imagePaths.get(position).getPhoto_url()).into(imgDisplay);

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
