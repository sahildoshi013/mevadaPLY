package com.example.sahilj.mevadaply.Adapters;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.sahilj.mevadaply.R;
import com.example.sahilj.mevadaply.Responses.DesignDetails;

import java.util.List;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class MyImageViewPagerAdapter extends PagerAdapter{


    private final List<DesignDetails> _imagePaths;
    private Activity _activity;

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

        LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = null;
        viewLayout = inflater.inflate(R.layout.content_display_image, container,
                false);
        imgDisplay = viewLayout.findViewById(R.id.imgDisplay);

        Glide.with(_activity).load(_imagePaths.get(position).getDesignImage()).into(imgDisplay).onLoadStarted(_activity.getResources().getDrawable(R.drawable.ic_placeholder));

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
