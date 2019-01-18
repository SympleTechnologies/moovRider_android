package com.moovapp.riderapp.preLogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.preLogin.signUp.SignUpActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTBaseActivity;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.SocialLoginResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;

public class Login extends LMTBaseActivity implements View.OnClickListener{

    GoogleSignInClient mGoogleSignInClient;
    private final int EMAIL_LOGIN_API = 1;
    private final int FORGOT_PASSWORD_API = 2;
    private final int FORGOT_PASSWORD_SUCCESS_DIALOG = 3;
    private final int SOCIAL_LOGIN_API = 4;


    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton loginButton;
    private ProfileTracker profileTracker;

    ImageView signInButton;
    TextView tvSignUp;
    TextView tvSignIn;
    ImageView fcbkImgView;
    private static final String TAG = "LogActivity";
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.welcome_page);
        FacebookSdk.sdkInitialize(getApplicationContext());
        tvSignIn = findViewById(R.id.tvSignIn);
        tvSignIn.setOnClickListener(this);
        tvSignUp = findViewById(R.id.tvSignUp);
        fbInIt();
        tvSignUp.setOnClickListener(this);
        loginButton = findViewById(R.id.login_button);
        fcbkImgView = findViewById(R.id.fcbkImgView);
        fcbkImgView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.d("googleres", "onActivityResult: "+requestCode);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

//
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                GoogleSignInAccount acct = result.getSignInAccount();
//                String tokenId = acct.getIdToken();
//                String name = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String email = acct.getEmail();
//                String social_id = acct.getId();
//                String social_image = String.valueOf(acct.getPhotoUrl());
//                Log.w("googleres", name + "," + personGivenName + "," + personFamilyName + "," + email + "," + social_id + "," + social_image);
//            } else {
//                Toast.makeText(this, "failed to login with google", Toast.LENGTH_SHORT).show();
//
//            }
//        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            String image = "";
            try {
                image = account.getPhotoUrl().toString();
            } catch (Exception e) {
                e.printStackTrace();
                image = "";
            }
            callSocialLoginApi("google", account.getId(), account.getEmail(), account.getDisplayName(), image);
            Log.d(TAG, "handleSignInResult: "+" , "+ account.getId()+" , "+ account.getEmail()+" , "+ account.getDisplayName()+" , "+ image + "android"+ appPrefes.getData(Constants.DEVICE_TOKEN)+ ", 2.0" +"google" );
            try {
                mGoogleSignInClient.signOut();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }


    private void callSocialLoginApi(final String loginType, final String id, final String email, final String name, final String profilePic) {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<SocialLoginResponseModel> call = apiService.socialLogin(loginType, id, "android", appPrefes.getData(Constants.DEVICE_TOKEN), "2.0");

                call.enqueue(new retrofit2.Callback<SocialLoginResponseModel>() {
                    @Override
                    public void onResponse(Call<SocialLoginResponseModel> call, Response<SocialLoginResponseModel> response) {
                        myProgressDialog.dismissProgress();
//                        Log.d(TAG, "onResponse: "+ response.body());
                        Log.d(TAG, "onResponse: "+ response.raw().body());
//                        Log.e("response", "onResponse: "+response.body().getData().getUser_details().getWallet_balance() );
                        Log.e("response", "onResponse: "+response.body().getData().getUser_details().getU_first_name() );
                        try {
                            if (!response.body().isStatus()) {
                                Intent intent = new Intent(Login.this, SignInSignUp.class);
                                intent.putExtra("RegistrationType", "Social");
                                intent.putExtra("loginType", loginType);
                                intent.putExtra("id", id);
                                intent.putExtra("email", email);
                                intent.putExtra("name", name);
                                intent.putExtra("profilePic", profilePic);
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                appPrefes.SaveData(Constants.USER_ID, response.body().getData().getUser_details().getU_id() + "");
                                appPrefes.SaveData(Constants.USER_FIRST_NAME, response.body().getData().getUser_details().getU_first_name() + "");
                                appPrefes.SaveData(Constants.ACCESS_TOKEN, response.body().getData().getAccess_token());
                                appPrefes.SaveData(Constants.USER_PROFILE_PIC, response.body().getData().getUser_pic_url());
                                appPrefes.SaveDataBoolean(Constants.USER_LOGGED_IN_STATUS, true);
                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(SOCIAL_LOGIN_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialLoginResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(SOCIAL_LOGIN_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(SOCIAL_LOGIN_API);
            }
        } else {
            showNoInternetAlert(SOCIAL_LOGIN_API);
        }
    }


    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity Response ", response.toString() + " " + object);
                            try {
                                if (object.getString("email") == null) {
                                    Toast.makeText(Login.this, "Email not available!", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("Social Login: " + "facebook " + object.getString("id") + " " + object.getString("name") + "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large" + object.getString("email"));
                                    myProgressDialog.setProgress(false);
                                    callSocialLoginApi("facebook", object.getString("id"), object.getString("email"), object.getString("name"), "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "Email not available!", Toast.LENGTH_SHORT).show();
                            }
                            LoginManager.getInstance().logOut();
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,link,picture");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        }
    };

    public void fbInIt() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        loginButton =  findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, callback);
    }

    public void fbLogin(View view) {
        loginButton.callOnClick();
    }
//    @Override
//    public void retryApiCall(int apiCode) {
//        super.retryApiCall(apiCode);
//        switch (apiCode) {
//            case EMAIL_LOGIN_API:
//                callEmailLoginApi();
//                break;
//            case FORGOT_PASSWORD_API:
//                callForgotPasswordApi();
//                break;
//        }
//    }


    private void updateUI(GoogleSignInAccount account) {
        Toast.makeText(this, ""+account.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button){
            signIn();
        }else if (v.getId() == R.id.tvSignUp){
            Intent i = new Intent(this, SignInSignUp.class);
            i.putExtra("firstTab", "SignUp");
            startActivity(i);
        }else if (v.getId() == R.id.fcbkImgView){
            loginButton.callOnClick();
//            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.tvSignIn){
            Intent i = new Intent(this, SignInSignUp.class);
            i.putExtra("firstTab", "SignIn");
            startActivity(i);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
    }
}
/*<?xml version="1.0" encoding="utf-8"?>
<ScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:weightSum="5"
        android:background="@color/grayLitest"
        tools:context=".main.fragments.SignIn">

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight=".8"
            android:src="@mipmap/logo" />

        <View
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight=".5" />


        <ImageView
            android:layout_gravity="center_horizontal"
            android:layout_width="wrap_content"
            android:layout_height="0dp"
            android:layout_weight=".4"
            android:src="@mipmap/lets_moov_text" />


        <LinearLayout
            android:orientation="vertical"
            android:padding="32dp"
            android:layout_weight="3.5"
            android:layout_width="match_parent"
            android:layout_height="0dp">


            <LinearLayout
                android:layout_marginLeft="16dp"
                android:layout_marginRight="@dimen/medium_dimens"
                android:orientation="horizontal"
                android:weightSum="2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <android.support.v7.widget.CardView
                    app:cardBackgroundColor="@color/white"
                    app:cardCornerRadius="17dp"
                    android:layout_marginRight="8dp"
                    android:layout_weight="1"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content">
                    <EditText
                        android:background="@drawable/border_white"
                        android:hint="First Name"
                        android:padding="15dp"
                        android:layout_width="match_parent"
                        android:layout_height="50dp" />
                </android.support.v7.widget.CardView>


                <android.support.v7.widget.CardView
                    app:cardBackgroundColor="@color/white"
                    app:cardCornerRadius="17dp"
                    android:layout_marginLeft="8dp"
                    android:layout_weight="1"
                    android:layout_width="0dp"
                    android:layout_height="wrap_content">
                    <EditText
                        android:background="@drawable/border_white"
                        android:hint="Surname"
                        android:padding="15dp"
                        android:layout_width="match_parent"
                        android:layout_height="50dp" />
                </android.support.v7.widget.CardView>
            </LinearLayout>

            <android.support.v7.widget.CardView
                app:cardBackgroundColor="@color/white"
                app:cardCornerRadius="17dp"
                android:layout_margin="16dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
                <EditText
                    android:background="@drawable/border_white"
                    android:hint="Email"
                    android:padding="15dp"
                    android:layout_width="match_parent"
                    android:layout_height="50dp" />
            </android.support.v7.widget.CardView>


            <android.support.v7.widget.CardView
                app:cardBackgroundColor="@color/white"
                app:cardCornerRadius="17dp"
                android:layout_margin="16dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
                <EditText
                    android:background="@drawable/border_white"
                    android:hint="Password"
                    android:inputType="textPassword"
                    android:padding="15dp"
                    android:layout_width="match_parent"
                    android:layout_height="50dp" />
            </android.support.v7.widget.CardView>


            <android.support.v7.widget.CardView
                app:cardBackgroundColor="@color/white"
                app:cardCornerRadius="17dp"
                android:layout_margin="16dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
                <EditText
                    android:background="@drawable/border_white"
                    android:hint="Confirm Password"
                    android:inputType="textPassword"
                    android:padding="15dp"
                    android:layout_width="match_parent"
                    android:layout_height="50dp" />
            </android.support.v7.widget.CardView>


            <TextView
                android:textColor="@color/gray"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textAlignment="center"
                android:text="Do You Have an Account? Sign In"/>


            <android.support.v7.widget.CardView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:layout_margin="20dp"
                app:cardBackgroundColor="#FF1493"
                app:cardCornerRadius="17dp"
                app:cardElevation="4dp">


                <TextView
                    android:id="@+id/tvSignInContinue"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:gravity="center"
                    android:padding="8dp"
                    android:textAllCaps="true"
                    android:text="Continue"
                    android:textColor="@color/white"
                    android:textSize="15sp" />


                <!--<LinearLayout-->
                <!--android:layout_width="match_parent"-->
                <!--android:layout_height="match_parent">-->

                <!--<ImageView-->
                <!--android:layout_marginLeft="10dp"-->
                <!--android:layout_width="wrap_content"-->
                <!--android:layout_height="wrap_content"-->
                <!--android:layout_gravity="center_vertical"-->
                <!--android:src="@drawable/ic_create_account" />-->


                <!--<TextView-->
                <!--android:id="@+id/tvSignIn"-->
                <!--android:layout_width="match_parent"-->
                <!--android:layout_height="wrap_content"-->
                <!--android:layout_gravity="center_vertical"-->
                <!--android:layout_margin="10dp"-->
                <!--android:gravity="center"-->
                <!--android:text="CREATE A NEW ACCOUNT"-->
                <!--android:textAlignment="center"-->
                <!--android:textColor="@color/white"-->
                <!--android:textSize="15sp" />-->


                <!--</LinearLayout>-->

            </android.support.v7.widget.CardView>

        </LinearLayout>



    </LinearLayout>
</ScrollView>
*/