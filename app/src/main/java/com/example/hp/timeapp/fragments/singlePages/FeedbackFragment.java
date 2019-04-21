package com.example.hp.timeapp.fragments.singlePages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.timeapp.R;

public class FeedbackFragment extends Fragment {

    private final String TAG = "FeedbackFragment";

//    public ActualFragment() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_feedback,container,false);


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
