package com.moovapp.riderapp.main.settings;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.preLogin.Login;
import com.moovapp.riderapp.preLogin.LoginActivity;
import com.moovapp.riderapp.utils.AppPreferences;
import com.moovapp.riderapp.utils.ConnectionDetector;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTFragmentHelper;
import com.moovapp.riderapp.utils.progress.MyProgressDialog;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.RequestOtpResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdateEmailResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdatePasswordResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdatePhoneResponseModel;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lijo Mathew Theckanal on 19-Jul-18.
 */

public class SettingsFragment extends LMTFragmentHelper {

    private final int EDIT_EMAIL_API = 1;
    private final int EMAIL_EDIT_SUCCESS_DIALOG = 2;
    private final int EDIT_PASSWORD_API = 3;
    private final int EMAIL_PASSWORD_SUCCESS_DIALOG = 4;
    private final int PHONE_SUCCESS_DIALOG = 5;
    private final int REQUEST_OTP_API = 6;
    private final int UPDATE_PHONE_API = 7;
    private static final int DIALOG_LOGOUT = 8;

    private MyProgressDialog myProgressDialog;
    public ConnectionDetector cd;
    public AppPreferences appPrefes;

    private String oldPassword = "";
    private String newPassword = "";
    private String oldEmail = "";
    private String newEmail = "";
    private String phoneNumber = "";
    private String countryCode = "";
    private String otp = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity());
        cd = new ConnectionDetector(getContext());
        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
        return view;
    }

    @OnClick(R.id.tvLogout)
    public void tvLogoutClick(){
        showAlertDialog(getContext(),"Logout", "Do you really want log out?", "Logout", "Cancel", DIALOG_LOGOUT);
    }

    @OnClick(R.id.tvResetPassword)
    public void tvResetPasswordClick() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_reset_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText edOldPassword = (EditText) dialog.findViewById(R.id.edOldPassword);
        final EditText edNewPassword = (EditText) dialog.findViewById(R.id.edNewPassword);
        final EditText edConfirmPassword = (EditText) dialog.findViewById(R.id.edConfirmPassword);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        final TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAllOkay = true;
                if (edOldPassword.getText().toString().trim().length() < 1) {
                    isAllOkay = false;
                    edOldPassword.setError("This field is required!");
                }
                if (edNewPassword.getText().toString().trim().length() < 1) {
                    isAllOkay = false;
                    edNewPassword.setError("This field is required!");
                }
                if (edConfirmPassword.getText().toString().trim().length() < 1) {
                    isAllOkay = false;
                    edConfirmPassword.setError("This field is required!");
                }
                if (!edConfirmPassword.getText().toString().equals(edNewPassword.getText().toString())) {
                    isAllOkay = false;
                    edConfirmPassword.setError("Passwords don't match!");
                }

                if (isAllOkay) {
                    oldPassword = edOldPassword.getText().toString();
                    newPassword = edNewPassword.getText().toString();
                    callEditPasswordApi();
                    dialog.dismiss();
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.tvResetNumber)
    public void tvResetNumberClick() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_reset_number);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText edPhoneNumber = (EditText) dialog.findViewById(R.id.edPhoneNumber);
        final CountryCodePicker codePicker = (CountryCodePicker) dialog.findViewById(R.id.codePicker);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        final TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPhoneNumber.getText().toString().trim().length() > 0) {
                    phoneNumber = edPhoneNumber.getText().toString();
                    countryCode = codePicker.getSelectedCountryCode();
                    callRequestOtpApi();
                    dialog.dismiss();
                } else {
                    edPhoneNumber.setError("This field is required!");
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showEnterOtpDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_enter_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText edOtp = (EditText) dialog.findViewById(R.id.edOtp);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        final TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edOtp.getText().toString().trim().length() > 0) {
                    otp = edOtp.getText().toString();
                    callVerifyOtpApi();
                    dialog.dismiss();
                } else {
                    edOtp.setError("This field is required!");
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
//
//    @OnClick(R.id.tvResetEmail)
//    public void tvResetEmailClick() {
//        final Dialog dialog = new Dialog(getContext());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_reset_email);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        final EditText edOldEmail = (EditText) dialog.findViewById(R.id.edOldEmail);
//        final EditText edNewEmail = (EditText) dialog.findViewById(R.id.edNewEmail);
//        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
//        final TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
//        tvOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isAllOkay = true;
//                if (edOldEmail.getText().toString().trim().length() < 1) {
//                    isAllOkay = false;
//                    edOldEmail.setError("This field is required!");
//                }
//                if (edNewEmail.getText().toString().trim().length() < 1) {
//                    isAllOkay = false;
//                    edNewEmail.setError("This field is required!");
//                }
//                if (isAllOkay) {
//                    oldEmail = edOldEmail.getText().toString();
//                    newEmail = edNewEmail.getText().toString();
//                    callEditEmailApi();
//                    dialog.dismiss();
//                }
//            }
//        });
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

    private void callEditEmailApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateEmailResponseModel> call = apiService.updateEmail(appPrefes.getData(Constants.USER_ID), oldEmail, newEmail);
                call.enqueue(new retrofit2.Callback<UpdateEmailResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdateEmailResponseModel> call, Response<UpdateEmailResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                showRequestSuccessDialog(getContext(), "Success", "Email updated successfully!", "Ok", EMAIL_EDIT_SUCCESS_DIALOG);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), EDIT_EMAIL_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateEmailResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), EDIT_EMAIL_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), EDIT_EMAIL_API);
            }
        } else {
            showNoInternetAlert(getContext(), EDIT_EMAIL_API);
        }
    }

    private void callEditPasswordApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<UpdatePasswordResponseModel> call = apiService.updatePassword(appPrefes.getData(Constants.USER_ID), oldPassword, newPassword);
                call.enqueue(new retrofit2.Callback<UpdatePasswordResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdatePasswordResponseModel> call, Response<UpdatePasswordResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                showRequestSuccessDialog(getContext(), "Success", "Password updated successfully!", "Ok", EMAIL_PASSWORD_SUCCESS_DIALOG);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), EDIT_PASSWORD_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdatePasswordResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), EDIT_PASSWORD_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), EDIT_PASSWORD_API);
            }
        } else {
            showNoInternetAlert(getContext(), EDIT_PASSWORD_API);
        }
    }

    private void callRequestOtpApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<RequestOtpResponseModel> call = apiService.requestOtp(appPrefes.getData(Constants.USER_ID), countryCode, phoneNumber);
                call.enqueue(new retrofit2.Callback<RequestOtpResponseModel>() {
                    @Override
                    public void onResponse(Call<RequestOtpResponseModel> call, Response<RequestOtpResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                showEnterOtpDialog();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), REQUEST_OTP_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestOtpResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), REQUEST_OTP_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), REQUEST_OTP_API);
            }
        } else {
            showNoInternetAlert(getContext(), REQUEST_OTP_API);
        }
    }

    private void callVerifyOtpApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<UpdatePhoneResponseModel> call = apiService.updatePhone(appPrefes.getData(Constants.USER_ID), countryCode, phoneNumber, otp);
                call.enqueue(new retrofit2.Callback<UpdatePhoneResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdatePhoneResponseModel> call, Response<UpdatePhoneResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                showRequestSuccessDialog(getContext(), "Success", "Phone number changed successfully", "Ok", PHONE_SUCCESS_DIALOG);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), UPDATE_PHONE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdatePhoneResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), UPDATE_PHONE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), UPDATE_PHONE_API);
            }
        } else {
            showNoInternetAlert(getContext(), UPDATE_PHONE_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case EDIT_EMAIL_API:
                callEditEmailApi();
                break;
            case EDIT_PASSWORD_API:
                callEditPasswordApi();
                break;
            case REQUEST_OTP_API:
                callRequestOtpApi();
                break;
            case UPDATE_PHONE_API:
                callVerifyOtpApi();
                break;
        }
    }

    @Override
    public void onClickAlertOkButton(int apiCode) {
        super.onClickAlertOkButton(apiCode);
        switch (apiCode) {
            case DIALOG_LOGOUT:
                appPrefes.clearData();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
