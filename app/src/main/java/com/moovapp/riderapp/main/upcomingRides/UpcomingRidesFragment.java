package com.moovapp.riderapp.main.upcomingRides;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ornolfr.ratingview.RatingView;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.main.moov.NotificationAction;
import com.moovapp.riderapp.utils.AppPreferences;
import com.moovapp.riderapp.utils.ConnectionDetector;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTFragmentHelper;
import com.moovapp.riderapp.utils.progress.MyProgressDialog;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.CancelRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCurrentRideResponseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lijo Mathew Theckanal on 18-Jul-18.
 */

public class UpcomingRidesFragment extends LMTFragmentHelper implements NotificationAction, SwipeRefreshLayout.OnRefreshListener, CancelRideInterface {

    private final int VIEW_CURRENT_RIDE_API = 1;
    private final int CANCEL_TRIP_API = 6;
    private final int SEARCH_FAILED_DIALOG = 5;
    private final int CANCEL_TRIP_DIALOG = 7;

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tvNoRides)
    TextView tvNoRides;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    private MyProgressDialog myProgressDialog;
    public ConnectionDetector cd;
    public AppPreferences appPrefes;

    private String currentRideId = "";
    public static NotificationAction notificationAction;
    private CancelRideInterface cancelRideInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_rides_fragment, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity());
        cd = new ConnectionDetector(getContext());
        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
        notificationAction = this;
        cancelRideInterface = this;
        swipeRefreshLayout.setOnRefreshListener(this);
        callViewCurrentRideApi();
        return view;
    }

    private void setValues(List<ViewCurrentRideResponseModel.DataEntity> data) {
        tvNoRides.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        UpcominRidesListAdapter upcominRidesListAdapter = new UpcominRidesListAdapter(getContext(), data,cancelRideInterface);
        listView.setAdapter(upcominRidesListAdapter);
    }

    @Override
    public void onReceveNotification(String rideId, String title) {
        callViewCurrentRideApi();
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
                        swipeRefreshLayout.setRefreshing(false);
                        try {
                            if (response.body().getStatus()) {
                                currentRideId = response.body().getData().get(0).getRideId() + "";
                                setValues(response.body().getData());
                            } else {
                                tvNoRides.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), VIEW_CURRENT_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewCurrentRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        swipeRefreshLayout.setRefreshing(false);
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), VIEW_CURRENT_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                swipeRefreshLayout.setRefreshing(false);
                showServerErrorAlert(getContext(), VIEW_CURRENT_RIDE_API);
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
            showNoInternetAlert(getContext(), VIEW_CURRENT_RIDE_API);
        }
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
                                showRequestSuccessDialog(getContext(), "Success", response.body().getMessage(), "Okay", SEARCH_FAILED_DIALOG);
//                                tvNoRides.setVisibility(View.VISIBLE);
//                                listView.setVisibility(View.GONE);
                                callViewCurrentRideApi();
                            } else {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), CANCEL_TRIP_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<CancelRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), CANCEL_TRIP_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), CANCEL_TRIP_API);
            }
        } else {
            showNoInternetAlert(getContext(), CANCEL_TRIP_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case VIEW_CURRENT_RIDE_API:
                callViewCurrentRideApi();
                break;
            case CANCEL_TRIP_API:
                callCancelRideApi();
                break;
        }
    }

    @Override
    public void onClickAlertOkButton(int apiCode) {
        super.onClickAlertOkButton(apiCode);
        switch (apiCode) {
            case CANCEL_TRIP_DIALOG:
                callCancelRideApi();
                break;
        }
    }

    @Override
    public void onRefresh() {
        callViewCurrentRideApi();
    }

    @Override
    public void onCancelRide(String rideId) {
        currentRideId = rideId;
        showAlertDialog(getContext(), "Cancel Ride", "Do you really want to cancel the ride?", "Yes", "No", CANCEL_TRIP_DIALOG);
    }
}
