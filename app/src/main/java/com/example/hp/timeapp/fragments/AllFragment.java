package com.example.hp.timeapp.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.hp.timeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment implements View.OnClickListener {

    private  final String TAG = "AllFragment";
    private Animation apper;
    private TextView txtclose;
    private View popupView;

    private PopupWindow popupWindow;
    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_all,container,false);



        ImageButton buttonBeauty = (ImageButton) view.findViewById(R.id.buttonBeauty);
        buttonBeauty.setOnClickListener(this);

        ImageButton buttonGames = (ImageButton) view.findViewById(R.id.buttonGames);
        buttonGames.setOnClickListener(this);

        ImageButton buttonMeds = (ImageButton) view.findViewById(R.id.buttonMed);
        buttonMeds.setOnClickListener(this);

        ImageButton buttonCarService = (ImageButton) view.findViewById(R.id.buttonCarService);
        buttonCarService.setOnClickListener(this);

        ImageButton buttonTourism = (ImageButton) view.findViewById(R.id.buttonTourism);
        buttonTourism.setOnClickListener(this);

        ImageButton buttonServices= (ImageButton) view.findViewById(R.id.buttonService);
        buttonServices.setOnClickListener(this);

            return view;
    }

    public void showPopup(View anchorView,Integer id) {

        popupView = getLayoutInflater().inflate(id,null,true);


        popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        popupWindow.showAtLocation(popupView,Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.BLACK));


        popupView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.popup));

//        apper = AnimationUtils.loadAnimation(getContext(),R.anim.translate);
        txtclose =(TextView) popupView.findViewById(R.id.txtclose);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // Initialize more widgets from `popup_layout.xml`
        // If the PopupWindow should be focusable

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());


        popupView = getLayoutInflater().inflate(id,null,true);

//
//        popupView = getLayoutInflater().inflate(id,null,true);
//
//        (id,getView(), false);

    }


    @Override
    public void onClick(View v) {
        Log.w(TAG,"POPUP  item is now working");
        switch (v.getId()){

            case R.id.buttonBeauty:
                showPopup(v,R.layout.select_popup_beauty);
                break;

            case R.id.buttonCarService:
                showPopup(v,R.layout.select_popup_car);
                break;

            case R.id.buttonMed:
                showPopup(v,R.layout.select_popup_med);
                break;
            case R.id.buttonGames:
                showPopup(v,R.layout.select_popup_entertaiment);
                break;
            case R.id.buttonTourism:
                showPopup(v,R.layout.select_popup_tourism);
                break;
            case R.id.buttonService:
                showPopup(v,R.layout.select_popup_services);
                break;
            default:
                Log.w(TAG,"DEFAULT Button PRESSED!");
                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
