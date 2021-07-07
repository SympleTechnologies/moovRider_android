package com.moovapp.riderapp.main.previousRides.rateDriver;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ornolfr.ratingview.RatingView;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTBaseActivity;
import com.moovapp.riderapp.utils.placesAutocomplete.AddDriverRatingResponseModel;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewDriverDetailsResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewPreviousRidesResponseModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Lijo Mathew Theckanal on 19-Jul-18.
 */

public class RateDriverActivity extends LMTBaseActivity {

    private final int VIEW_DRIVER_DETAILS_API = 1;
    private final int RATE_DRIVER_API = 2;

    @BindView(R.id.imgDriver)
    ImageView imgDriver;
    @BindView(R.id.tvDriverName)
    TextView tvDriverName;
    @BindView(R.id.rating1)
    RatingView rating1;
    @BindView(R.id.edMessage)
    EditText edMessage;

    private ViewPreviousRidesResponseModel.DataEntity dataEntity;
    private String currentDriverId = "";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.rate_driver_activity);
        ButterKnife.bind(this);
        dataEntity = (ViewPreviousRidesResponseModel.DataEntity) getIntent().getExtras().get("Details");
        currentDriverId = dataEntity.getRide_driver_id() + "";
        callViewDriverDetails();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.navBackButton)
    public void navBackButtonClick() {
        finish();
    }

    @OnClick(R.id.tvSubmit)
    public void tvSubmitClick() {
        callRateDriverApi();
    }

    private void callViewDriverDetails() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ViewDriverDetailsResponseModel> call = apiService.viewDriverDetails("view/details/driver/" + currentDriverId);
                call.enqueue(new retrofit2.Callback<ViewDriverDetailsResponseModel>() {
                    @Override
                    public void onResponse(Call<ViewDriverDetailsResponseModel> call, Response<ViewDriverDetailsResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                try {
                                    if (response.body().getData().getUser_pic_url().length() > 3) {
                                        Picasso.get().load(response.body().getData().getUser_pic_url()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(imgDriver);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                tvDriverName.setText(response.body().getData().getUser_details().getFirst_name() + " " + response.body().getData().getUser_details().getLast_name());
                                try {
                                    float rating = (float) response.body().getData().getUser_details().getRatings();
                                    rating1.setRating(rating);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(VIEW_DRIVER_DETAILS_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewDriverDetailsResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_DRIVER_DETAILS_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(VIEW_DRIVER_DETAILS_API);
            }
        } else {
            showNoInternetAlert(VIEW_DRIVER_DETAILS_API);
        }
    }

    private void callRateDriverApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                String message = edMessage.getText().toString();
                if (message.trim().length() == 0) {
                    message = "The ride was awesome...";
                }
                Call<AddDriverRatingResponseModel> call = apiService.addDriverRating(currentDriverId, dataEntity.getRide_trip_id() + "", appPrefes.getData(Constants.USER_ID), rating1.getRating() + "", message);
                call.enqueue(new retrofit2.Callback<AddDriverRatingResponseModel>() {
                    @Override
                    public void onResponse(Call<AddDriverRatingResponseModel> call, Response<AddDriverRatingResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                showRequestSuccessDialog("Success", response.body().getMessage(), "Ok", 1);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(RATE_DRIVER_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddDriverRatingResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(RATE_DRIVER_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(RATE_DRIVER_API);
            }
        } else {
            showNoInternetAlert(RATE_DRIVER_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case RATE_DRIVER_API:
                callRateDriverApi();
                break;
            case VIEW_DRIVER_DETAILS_API:
                callViewDriverDetails();
                break;
        }
    }
}
