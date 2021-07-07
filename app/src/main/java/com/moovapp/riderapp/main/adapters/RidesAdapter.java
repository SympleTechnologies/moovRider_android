package com.moovapp.riderapp.main.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.moovapp.riderapp.main.fragments.PreviousRides;
import com.moovapp.riderapp.main.fragments.SignIn;
import com.moovapp.riderapp.main.fragments.SignUp;
import com.moovapp.riderapp.main.fragments.UpcomingRides;

import java.util.Map;

/**
 * Created by Manuel Chris-Ogar on 2/15/2019.
 */

public class RidesAdapter extends FragmentStatePagerAdapter {



    int numTabs;
    String firstTab;
    public RidesAdapter(FragmentManager fm, int noOfTabs, String firstTab){
        super(fm);
        this.numTabs = noOfTabs;
        this.firstTab = firstTab;

    }

    @Override
    public Fragment getItem(int position) {

      if (TextUtils.equals(firstTab, "previous")){
            switch (position){
                case 0: return new PreviousRides();
                case 1: return new UpcomingRides();
            }

        }else{
            switch (position){
                case 0: return new UpcomingRides();
                case 1: return new PreviousRides();
            }
        }



        return null;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}

