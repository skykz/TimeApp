package com.example.hp.timeapp.adapters.single_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hp.timeapp.fragments.singlePages.FeedbackFragment;
import com.example.hp.timeapp.fragments.singlePages.InfoFragment;

public class SingleAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public SingleAdapter(FragmentManager fm,int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InfoFragment();
            case 1:
                return new FeedbackFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
