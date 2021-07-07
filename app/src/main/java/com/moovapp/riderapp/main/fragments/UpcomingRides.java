package com.moovapp.riderapp.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.adapters.UpcomingRidesAdapter;
import com.moovapp.riderapp.main.moov.NotificationAction;
import com.moovapp.riderapp.main.upcomingRides.CancelRideInterface;
import com.moovapp.riderapp.main.upcomingRides.UpcominRidesListAdapter;
import com.moovapp.riderapp.utils.AppPreferences;
import com.moovapp.riderapp.utils.ConnectionDetector;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTFragment;
import com.moovapp.riderapp.utils.progress.MyProgressDialog;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.CancelRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCurrentRideResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Manuel Chris-Ogar on 2/14/2019.
 */
public class UpcomingRides extends LMTFragment implements  SwipeRefreshLayout.OnRefreshListener{

    public UpcomingRides(){

    }

    private final int VIEW_CURRENT_RIDE_API = 1;
    private final int CANCEL_TRIP_API = 6;
    private final int SEARCH_FAILED_DIALOG = 5;
    private final int CANCEL_TRIP_DIALOG = 7;

//    @BindView(R.id.listView)
//    ListView listView;
    @BindView(R.id.tvNoRides)
    TextView tvNoRides;
//    @BindView(R.id.reloadUpcomingRides)
    SwipeRefreshLayout swipeRefreshLayout;


    private MyProgressDialog myProgressDialog;
    public ConnectionDetector cd;
    public AppPreferences appPrefes;

    private String currentRideId = "";
    public static NotificationAction notificationAction;
    private CancelRideInterface cancelRideInterface;

    private RecyclerView recyclerView;
    private List<ViewCurrentRideResponseModel.DataEntity> data;
    private UpcomingRidesAdapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_upcoming_rides, container, false);
        ButterKnife.bind(this, v);
        swipeRefreshLayout = v.findViewById(R.id.reloadUpcomingRides);
        recyclerView = v.findViewById(R.id.recycleUpcomingTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        myProgressDialog = new MyProgressDialog(getActivity());
        cd = new ConnectionDetector(getContext());
        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
        data = new ArrayList<>();
        callViewCurrentRideApi();
        swipeRefreshLayout.setOnRefreshListener(this);
        return  v;
    }

    private void callViewCurrentRideApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ViewCurrentRideResponseModel> call = apiService.viewCurrentRide("view/rides/current/user/" + appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<ViewCurrentRideResponseModel>() {
                    @Override
                    public void onResponse(Call<ViewCurrentRideResponseModel> call, Response<ViewCurrentRideResponseModel> response) {
                        myProgressDialog.dismissProgress();
//                        swipeRefreshLayout.setRefreshing(false);
                        try {
                            if (response.body().getStatus()) {
                                currentRideId = response.body().getData().get(0).getRideId() + "";
                                setValues(response.body().getData());
                            } else {
                                tvNoRides.setVisibility(View.VISIBLE);
//                                recyclerView.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(VIEW_CURRENT_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewCurrentRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
//                        swipeRefreshLayout.setRefreshing(false);
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_CURRENT_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
//                swipeRefreshLayout.setRefreshing(false);
                showServerErrorAlert(VIEW_CURRENT_RIDE_API);
            }
        } else {
//            swipeRefreshLayout.setRefreshing(false);

            showNoInternetAlert(VIEW_CURRENT_RIDE_API);
        }
    }

    private void setValues(List<ViewCurrentRideResponseModel.DataEntity> info) {
        tvNoRides.setVisibility(View.GONE);
        data.clear();
        data.addAll(info);
        adapter = new UpcomingRidesAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void callCancelRideApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<CancelRideResponseModel> call = apiService.cancelRide("rides/cancel/" + currentRideId+"/free");
                call.enqueue(new retrofit2.Callback<CancelRideResponseModel>() {
                    @Override
                    public void onResponse(Call<CancelRideResponseModel> call, Response<CancelRideResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                showRequestSuccessDialog("Success", response.body().getMessage(), "Okay", SEARCH_FAILED_DIALOG);
//                                tvNoRides.setVisibility(View.VISIBLE);
//                                listView.setVisibility(View.GONE);
                                callViewCurrentRideApi();
                            } else {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(CANCEL_TRIP_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<CancelRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(CANCEL_TRIP_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(CANCEL_TRIP_API);
            }
        } else {
            showNoInternetAlert(CANCEL_TRIP_API);
        }
    }

    @Override
    public void onRefresh() {
        callViewCurrentRideApi();
    }
}
