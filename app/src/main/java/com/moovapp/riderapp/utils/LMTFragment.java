package com.moovapp.riderapp.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.utils.progress.MyProgressDialog;

import retrofit2.http.GET;


public class LMTFragment extends Fragment implements NetworkChangeReceiver.Internet {
    public AppPreferences appPrefes;
    public ProgressDialog progressDialog;
    public ConnectionDetector cd;
    public MyProgressDialog myProgressDialog;
    public LMTBaseActivity  mainActivity;

//    @Override
//    protected void onCreate(Bundle arg0) {
//        // Show status bar
//        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        super.onCreate(arg0);
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
//        cd = new ConnectionDetector(getContext());
//        myProgressDialog = new MyProgressDialog(getActivity());
//        NetworkChangeReceiver.internet = this;
//    }


    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (LMTBaseActivity) getActivity();
        mainActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
        cd = new ConnectionDetector(getContext());
        myProgressDialog = new MyProgressDialog(getActivity());
        NetworkChangeReceiver.internet = this;
    }

    public void showNoInternetAlert(final int apiCode) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_no_internet);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvRetry = (TextView) dialog.findViewById(R.id.tvRetry);
        final TextView tvClose = (TextView) dialog.findViewById(R.id.tvClose);
        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                retryApiCall(apiCode);
            }
        });
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showServerErrorAlert(final int apiCode) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_server_error);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvRetry = (TextView) dialog.findViewById(R.id.tvRetry);
        final TextView tvClose = (TextView) dialog.findViewById(R.id.tvClose);
        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                retryApiCall(apiCode);
            }
        });
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showRequestSuccessDialog(String title, String message, String button, final int code) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_popup);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        final TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvCancel.setText(button);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSuccessDialogClick(code);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showAlertDialog(String title, String message, String okButton, String cancelButton, final int code) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        final TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        final TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvOk.setText(okButton);
        tvCancel.setText(cancelButton);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAlertOkButton(code);
                dialog.dismiss();
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

    public void retryApiCall(int apiCode) {

    }

    public void onClickAlertOkButton(int apiCode) {

    }

    public void onSuccessDialogClick(int apiCode) {

    }

    @Override
    public void net() {

    }
}


