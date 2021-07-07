package com.moovapp.riderapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moovapp.riderapp.main.adapters.RidesAdapter;
import com.moovapp.riderapp.utils.LMTFragment;

/**
 * Created by Manuel Chris-Ogar on 2/14/2019.
 */
public class TripsFragment extends LMTFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_trips, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutTrips);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.white));

        Bundle args = getArguments();
        if (args != null) {
            String firstTab = "firstTab";
            if (TextUtils.equals(args.getString(firstTab), "upcoming")) {
                tabLayout.addTab(tabLayout.newTab().setText("Upcoming Rides"));
                tabLayout.addTab(tabLayout.newTab().setText("Previous Rides"));
            } else {
                tabLayout.addTab(tabLayout.newTab().setText("Upcoming Rides"));
                tabLayout.addTab(tabLayout.newTab().setText("Previous Rides"));

            }

            tabLayout.setTabTextColors(getResources().getColor(R.color.gray), getResources().getColor(R.color.black));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = view.findViewById(R.id.pagerTrips);

            final RidesAdapter pagerAdapter = new RidesAdapter(getChildFragmentManager(), tabLayout.getTabCount(), firstTab);
            viewPager.setAdapter(pagerAdapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        return view;
    }

}




