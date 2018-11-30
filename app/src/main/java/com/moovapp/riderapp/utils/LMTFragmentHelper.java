package com.moovapp.riderapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.moovapp.riderapp.R;


public class LMTFragmentHelper extends Fragment {

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            int transition, String name, Context context) {
//
//		FragmentTransaction ft = ((FragmentActivity) context)
//				.getSupportFragmentManager().beginTransaction();
//		ft.add(R.id.container, fragment);
//		ft.setTransition(transition);
//		if (addToBackStack)
//			ft.addToBackStack(name);
//		ft.commit();
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack,
                                int transition, String name, Context context) {

//		FragmentTransaction ft = ((FragmentActivity) context)
//				.getSupportFragmentManager().beginTransaction();
//		ft.replace(R.id.container, fragment);
//		ft.setTransition(transition);
//		if (addToBackStack)
//			ft.addToBackStack(name);
//		ft.commit();
    }


    public void showNoInternetAlert(Context context, final int apiCode) {
        final Dialog dialog = new Dialog(context);
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

    public void showServerErrorAlert(Context context, final int apiCode) {
        final Dialog dialog = new Dialog(context);
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

    public void showRequestSuccessDialog(Context context, String title, String message, String button, final int code) {
        final Dialog dialog = new Dialog(context);
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

    public void showAlertDialog(Context context, String title, String message, String okButton, String cancelButton, final int code) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        final ImageView tvOk = dialog.findViewById(R.id.tvOk);
        final ImageView tvCancel = dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(title);
        tvMessage.setText(message);
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

    public void onClickAlertOkButton(int apiCode) {

    }

    public void onSuccessDialogClick(int apiCode) {

    }

    public void retryApiCall(int apiCode) {

    }


}
