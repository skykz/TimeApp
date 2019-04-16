package com.example.hp.timeapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hp.timeapp.fragments.ActualFragment;
import com.example.hp.timeapp.fragments.AllFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }
//    public PageAdapter(FragmentManager fm) {
//        super(fm);
//    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActualFragment();
            case 1:
                return new AllFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
