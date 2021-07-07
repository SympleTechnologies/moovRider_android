package com.moovapp.riderapp.main.adapters;
/**
 * Created by Manuel Chris-Ogar on 1/23/2019.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.moovapp.riderapp.main.fragments.SignIn;
import com.moovapp.riderapp.main.fragments.SignUp;

import java.util.Map;

public class PagerAdapter extends FragmentStatePagerAdapter {



    int numTabs;
    String firstTab;
    Map<String, String> socialRegistrationDetails;
    public PagerAdapter(FragmentManager fm, int noOfTabs, String firstTab, Map<String, String> socialRegistrationDetails) {
        super(fm);
        this.numTabs = noOfTabs;
        this.firstTab = firstTab;
        this.socialRegistrationDetails = socialRegistrationDetails;
    }

    @Override
    public Fragment getItem(int position) {

        if (TextUtils.equals(firstTab, "SignIn")){
            switch (position){
                case 0: return new SignIn();
                case 1: return new SignUp();
            }
        }else{
            if (socialRegistrationDetails != null){
                Bundle args = new Bundle();
                args.putString("RegistrationType", "Social");
                args.putString("loginType", socialRegistrationDetails.get("loginType") );
                args.putString("email", socialRegistrationDetails.get("email"));
                args.putString("name", socialRegistrationDetails.get("name"));
                args.putString("profilePic",socialRegistrationDetails.get("profilePic"));
                args.putString("loginType", socialRegistrationDetails.get("loginType"));
                args.putString("id",socialRegistrationDetails.get("id"));
                SignUp signUp =  new SignUp();
                signUp.setArguments(args);
                switch (position){
                    case 0: return signUp;
                    case 1: return new SignIn();
                }
            }else{
                switch (position){
                    case 0: return new SignUp();
                    case 1: return new SignIn();
                }
            }

        }



        return null;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
