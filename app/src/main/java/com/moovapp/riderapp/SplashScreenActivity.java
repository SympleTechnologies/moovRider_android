package com.moovapp.riderapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.preLogin.LandingPageActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lijo Mathew Theckanal on 17-Jul-18.
 */

public class SplashScreenActivity extends LMTBaseActivity {

    private final int SPLASH_SCREEN_TIME = 1500;

    @BindView(R.id.splash_image)
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
        delayFlow();
        getDeviceToken();
        splashImage.setAlpha(-40);
    }

    private void delayFlow() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(appPrefes.getDataBoolean(Constants.USER_LOGGED_IN_STATUS)){
                    Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreenActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_SCREEN_TIME);
    }

    public void getDeviceToken() {
        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
        String token = FirebaseInstanceId.getInstance().getToken();
        appPrefes.SaveData(Constants.DEVICE_TOKEN, token);
    }
}
