package com.moovapp.riderapp.preLogin;
/**
 * Created by Manuel Chris-Ogar on 1/10/2019.
 */
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.adapters.PagerAdapter;
import com.moovapp.riderapp.utils.LMTBaseActivity;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignInSignUp extends LMTBaseActivity {

    private String firstTab;
    Map<String, String> registrationDetails ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);
        registrationDetails = new HashMap<>();
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
        if (TextUtils.equals(getIntent().getStringExtra("loginType"), "google") || TextUtils.equals(getIntent().getStringExtra("loginType"), "facebook")){
            String socialLoginType   = getIntent().getStringExtra("loginType");
            String socialLoginId =  getIntent().getStringExtra("id");
            String socialEmail =  getIntent().getStringExtra("email");
            String socialName =  getIntent().getStringExtra("name");
            String profilePic = getIntent().getStringExtra("profilePic");
            registrationDetails.put("loginType", socialLoginType);
            registrationDetails.put("email", socialEmail);
            registrationDetails.put("name", socialName);
            registrationDetails.put("id", socialLoginId);
            registrationDetails.put("profilePic", profilePic);
        }

        firstTab =  getIntent().getStringExtra("firstTab");

        if (TextUtils.equals(firstTab, "SignIn")){
            tabLayout.addTab(tabLayout.newTab().setText("Sign In"));
            tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        }else{
            tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
            tabLayout.addTab(tabLayout.newTab().setText("Sign In"));
        }

        tabLayout.setTabTextColors(getResources().getColor(R.color.gray), getResources().getColor(R.color.black));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), firstTab, registrationDetails);
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


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
