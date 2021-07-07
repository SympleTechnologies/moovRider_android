package com.moovapp.riderapp.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.adapters.PreviousRidesAdapter;
import com.moovapp.riderapp.main.previousRides.PreviousRidesListAdapter;
import com.moovapp.riderapp.main.previousRides.rateDriver.RateDriverActivity;
import com.moovapp.riderapp.utils.AppPreferences;
import com.moovapp.riderapp.utils.ConnectionDetector;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTFragment;
import com.moovapp.riderapp.utils.progress.MyProgressDialog;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewPreviousRidesResponseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Manuel Chris-Ogar on 2/14/2019.
 */
public class PreviousRides extends LMTFragment {


    public PreviousRides(){

    }

    RecyclerView recyclerView;
    PreviousRidesAdapter previousRidesAdapter;
    private PreviousRidesAdapter previousRidesListAdapter;
    private List<ViewPreviousRidesResponseModel.DataEntity> dataEntityList;
    private final int VIEW_PREVIOUS_RIDE_API = 1;
    TextView tvNoRides;

    private MyProgressDialog myProgressDialog;
    public ConnectionDetector cd;
    public AppPreferences appPrefes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.previous_rides_tab, container, false);
        ButterKnife.bind(this, view);
        recyclerView = view.findViewById(R.id.previousRidesRecyclerView);
        tvNoRides  = view.findViewById(R.id.tvNoRides);
        myProgressDialog = new MyProgressDialog(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        dataEntityList = new ArrayList<>();
        previousRidesAdapter = new PreviousRidesAdapter(dataEntityList, new PreviousRidesAdapter.ListItemClick() {
            @Override
            public void clickedTrip(ViewPreviousRidesResponseModel.DataEntity tripDetails) {
                Intent intent = new Intent(getActivity(), RateDriverActivity.class);
                intent.putExtra("Details", (Serializable) tripDetails);
                startActivity(intent);
            }
        });



        cd = new ConnectionDetector(getContext());
        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
        callViewPreviousRideApi();
        return view;
            }

    private void callViewPreviousRideApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Log.d("ridetype", "onResponse: rides");
                Call<ViewPreviousRidesResponseModel> call = apiService.viewPreviousRide("view/rides/user/" + appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<ViewPreviousRidesResponseModel>() {
                    @Override
                    public void onResponse(Call<ViewPreviousRidesResponseModel> call, final Response<ViewPreviousRidesResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {

                                for (ViewPreviousRidesResponseModel.DataEntity dataEntity : response.body().getData()){
//                                    if (!TextUtils.equals(dataEntity.getRide_type(), null)){
//                                        Log.d("ridetype", "onResponse: "+dataEntity.getRide_type());
//                                        dataEntityList.clear();
                                        dataEntityList.add(dataEntity);
//                                    }
                                }if (dataEntityList.size() != 0){
                                    recyclerView.setAdapter(previousRidesAdapter);
                                    previousRidesAdapter.notifyDataSetChanged();
                                }else{
                                    tvNoRides.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }


                            } else {
                                tvNoRides.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(VIEW_PREVIOUS_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewPreviousRidesResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_PREVIOUS_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(VIEW_PREVIOUS_RIDE_API);
            }
        } else {
            showNoInternetAlert(VIEW_PREVIOUS_RIDE_API);
        }
    }


    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case VIEW_PREVIOUS_RIDE_API:
                callViewPreviousRideApi();
                break;
        }
    }
}
