package com.example.hp.timeapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import com.example.hp.timeapp.ui.CertainActivity;
import com.example.hp.timeapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActualFragment extends Fragment {

    private final String TAG = "ActualFragment";
    private long lastClickTime = 0;

//    public ActualFragment() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_actual,container,false);
        final ImageButton buttonCar = (ImageButton) view.findViewById(R.id.buttonCarService);

        buttonCar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // preventing double, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - lastClickTime < 1500){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                Log.w(TAG,"Select item is now working");
                Intent myIntent = new Intent(getActivity(), CertainActivity.class);
                startActivity(myIntent);
            }
        });


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
