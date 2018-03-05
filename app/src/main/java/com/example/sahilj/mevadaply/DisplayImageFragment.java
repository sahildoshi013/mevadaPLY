package com.example.sahilj.mevadaply;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sahilj.mevadaply.Adapters.MyImageViewPagerAdapter;
import com.example.sahilj.mevadaply.Responses.DesignDetails;
import com.example.sahilj.mevadaply.Utils.MyConstants;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayImageFragment extends Fragment {


    private List<DesignDetails> data;
    private int position;
    private ViewPager mPager;
    private Button mButton;

    public DisplayImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_image, container, false);

        Bundle bundle = getArguments();
        position = bundle.getInt(MyConstants.CURRENT_SELECTED_ID);
        data = (List<DesignDetails>) bundle.getSerializable(MyConstants.DATA);

        mPager = view.findViewById(R.id.pager);
        mButton = view.findViewById(R.id.btnClose);

        mPager.setAdapter(new MyImageViewPagerAdapter(getActivity(),data));
        mPager.setCurrentItem(position);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

}
