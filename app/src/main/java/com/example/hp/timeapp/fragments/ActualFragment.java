package com.example.hp.timeapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
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

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

                Intent myIntent = new Intent(getContext(), CertainActivity.class);
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
