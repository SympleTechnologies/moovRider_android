package com.moovapp.riderapp.main.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.preLogin.LoginActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTFragment;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.LoginEmailResponseModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn extends LMTFragment  {

    private final int EMAIL_LOGIN_API = 1;
    private final int FORGOT_PASSWORD_API = 2;
    private final int FORGOT_PASSWORD_SUCCESS_DIALOG = 3;
    private final int SOCIAL_LOGIN_API = 4;

    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edPassword)
    EditText edPassword;
    TextView tvSignInContinue;

    @BindView(R.id.loginHasFailed)
    TextView loginHasFailed;

    private String forgotPasswordEmail = "";


    public SignIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(view);
        getDeviceToken();
        loginHasFailed = view.findViewById(R.id.loginHasFailed);
        tvSignInContinue = view.findViewById(R.id.tvSignInContinue);
        tvSignInContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edEmail.getText()) ){
                    edEmail.setError("Email Field Cannot be Empty");
                    return;
                }else if (TextUtils.isEmpty(edPassword.getText()) ){
                    edPassword.setError("Password Field cannot be Empty");
                    return;
                }
                callEmailLoginApi();
            }
        });
        edEmail = view.findViewById(R.id.edEmail);
        edPassword = view.findViewById(R.id.edPassword);
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        try {
            PackageInfo info = mainActivity.getPackageManager().getPackageInfo(
                    "com.moovapp.riderapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void getDeviceToken() {
        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
        String token = FirebaseInstanceId.getInstance().getToken();
        appPrefes.SaveData(Constants.DEVICE_TOKEN, token);
    }

    private void callEmailLoginApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<LoginEmailResponseModel> call = apiService.loginEmail(edEmail.getText().toString(), edPassword.getText().toString(), "android", appPrefes.getData(Constants.DEVICE_TOKEN), appPrefes.getData(Constants.DEVICE_TOKEN), "2.0");
                call.enqueue(new retrofit2.Callback<LoginEmailResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginEmailResponseModel> call, Response<LoginEmailResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                loginHasFailed.setVisibility(View.VISIBLE);
//                                    Toast.makeText(getContext(), "401 error" , Toast.LENGTH_SHORT).show();
                            } else {
                                appPrefes.SaveData(Constants.USER_ID, response.body().getData().getUser_details().getU_id() + "");
                                appPrefes.SaveData(Constants.USER_FIRST_NAME, response.body().getData().getUser_details().getU_first_name() + "");
                                appPrefes.SaveData(Constants.ACCESS_TOKEN, response.body().getData().getAccess_token());
                                appPrefes.SaveData(Constants.USER_PROFILE_PIC, response.body().getData().getUser_pic_url());
                                appPrefes.SaveDataBoolean(Constants.USER_LOGGED_IN_STATUS, true);
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(intent);
                                mainActivity.finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(EMAIL_LOGIN_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginEmailResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(EMAIL_LOGIN_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(EMAIL_LOGIN_API);
            }
        } else {
            showNoInternetAlert(EMAIL_LOGIN_API);
        }
    }
}
