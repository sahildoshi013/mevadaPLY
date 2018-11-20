package com.example.sahilj.mevadaply;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.sahilj.mevadaply.Adapters.MyGridViewAdapter;
import com.example.sahilj.mevadaply.Responses.DesignDetailResult;
import com.example.sahilj.mevadaply.Responses.DesignDetails;
import com.example.sahilj.mevadaply.Utils.MyConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sahilj.mevadaply.Utils.MyConstants.apiInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayDesignFragment extends Fragment {

    private static final String TAG = "Display Fragment";
    private int position;
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private MyGridViewAdapter mGridAdapter;
    private List<DesignDetails> mGridData;

    public DisplayDesignFragment() {
        // Required empty public constructor
        position=0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_design, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            position = bundle.getInt(MyConstants.CURRENT_SELECTED_ID);
        Log.v(TAG,"Position "+position);

        mGridView = view.findViewById(R.id.gridView);
        mProgressBar = view.findViewById(R.id.progressBar);


        //Start download
        getDesignDetails();
        mProgressBar.setVisibility(View.VISIBLE);

        return view;
    }

    private void getDesignDetails() {
        Log.v(TAG,"Fetch " + position);
        Call<DesignDetailResult> call=apiInterface.getDesignDetails(position);
        call.enqueue(new Callback<DesignDetailResult>() {
            @Override
            public void onResponse(Call<DesignDetailResult> call, Response<DesignDetailResult> response) {
                if(response.body().isStatus()){
                    Log.v(TAG,"Done ");
                    mGridData=response.body().getData();
                    mGridAdapter = new MyGridViewAdapter(getContext(), R.layout.content_grid_layout, mGridData,getFragmentManager());
                    mGridView.setAdapter(mGridAdapter);
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DesignDetailResult> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.v(TAG,"Fail ",t);
            }
        });
    }

}
