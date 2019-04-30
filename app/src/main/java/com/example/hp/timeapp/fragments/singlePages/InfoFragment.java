package com.example.hp.timeapp.fragments.singlePages;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.timeapp.R;
import com.example.hp.timeapp.adapters.single_adapter.SingleAdapter;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class InfoFragment extends Fragment {

    private final String TAG = "InfoFragment";

    private TabLayout tabLayout;
    private TabItem tabActual;
    private TabItem tabAll;
    private ViewPager viewPager;
    private SingleAdapter singleAdapter;

    private CarouselView carouselView;
    private int[] sampleImages = { R.drawable.time_green,R.drawable.avatar_lady,R.drawable.ic_make_up,R.drawable.avatar_lady};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info,container,false);

        carouselView = (CarouselView) view.findViewById(R.id.viewPager_slider);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

//
//        tabLayout = view.findViewById(R.id.tablayout);
//        tabActual = view.findViewById(R.id.actual_tab);
//        tabAll = view.findViewById(R.id.all_tab);
//
//        viewPager = view.findViewById(R.id.viewSingle);
//
//        singleAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pageAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        viewPager = (ViewPager)view.findViewById(R.id.viewPager_slider);
//
//        TabLayout tabLayout1 = (TabLayout) view.findViewById(R.id.tabDots);
//        tabLayout1.setupWithViewPager(viewPager, true);

        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource((sampleImages[position]));
        }
    };
    @Override
    public void onPause() {
        super.onPause();

    }
}
