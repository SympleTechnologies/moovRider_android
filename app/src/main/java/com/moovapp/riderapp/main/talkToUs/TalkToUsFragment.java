package com.moovapp.riderapp.main.talkToUs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.utils.AppPreferences;
import com.moovapp.riderapp.utils.ConnectionDetector;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTFragmentHelper;
import com.moovapp.riderapp.utils.progress.MyProgressDialog;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.TalkToUsResponseModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lijo Mathew Theckanal on 19-Jul-18.
 */

public class TalkToUsFragment extends LMTFragmentHelper {

    private final int EMAIL_SENT_SUCCESS_DIALOG = 1;
    private final int CONTACT_US_API = 2;

    @BindView(R.id.edSubject)
    EditText edSubject;
    @BindView(R.id.edMessage)
    EditText edMessage;

    private MyProgressDialog myProgressDialog;
    public ConnectionDetector cd;
    public AppPreferences appPrefes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.talk_to_us, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity());
        cd = new ConnectionDetector(getContext());
        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
        return view;
    }

    @OnClick(R.id.tvSubmit)
    public void tvSubmitClick() {
        if (edSubject.getText().toString().trim().length() < 1) {
            edSubject.setError("This field is required");
        }
        if (edMessage.getText().toString().trim().length() < 1) {
            edMessage.setError("This field is required");
        }
        if (edSubject.getText().toString().trim().length() > 0 && edMessage.getText().toString().trim().length() > 0) {
            callTalkToUsApi();
        }
    }

    private void callTalkToUsApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TalkToUsResponseModel> call = apiService.talkToUs(appPrefes.getData(Constants.USER_ID), appPrefes.getData(Constants.USER_FIRST_NAME), edSubject.getText().toString(), edMessage.getText().toString());
                call.enqueue(new retrofit2.Callback<TalkToUsResponseModel>() {
                    @Override
                    public void onResponse(Call<TalkToUsResponseModel> call, Response<TalkToUsResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                edSubject.setText("");
                                edMessage.setText("");
                                showRequestSuccessDialog(getContext(),"Email Sent", response.body().getMessage(), "Okay", EMAIL_SENT_SUCCESS_DIALOG);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(),CONTACT_US_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<TalkToUsResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(),CONTACT_US_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(),CONTACT_US_API);
            }
        } else {
            showNoInternetAlert(getContext(),CONTACT_US_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case CONTACT_US_API:
                callTalkToUsApi();
                break;
        }
    }
}
