package com.moovapp.riderapp.main.moov;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ornolfr.ratingview.RatingView;
import com.google.android.gms.maps.model.LatLng;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.wallet.WalletActivity;
import com.moovapp.riderapp.utils.AppPreferences;
import com.moovapp.riderapp.utils.ConnectionDetector;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.GPSTracker;
import com.moovapp.riderapp.utils.LMTFragmentHelper;
import com.moovapp.riderapp.utils.placesAutocomplete.CustomAutoCompleteTextView;
import com.moovapp.riderapp.utils.placesAutocomplete.PlaceJSONParser;
import com.moovapp.riderapp.utils.progress.MyProgressDialog;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.BookRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.CancelRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.RideSearchResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCollegesResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewWalletBalanceResponseModel;
import com.moovapp.riderapp.utils.spinnerAdapter.WhiteSpinnerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Lijo Mathew Theckanal on 18-Jul-18.
 */

public class MoovFragment extends LMTFragmentHelper implements NotificationAction {

    private static final String GOOGLE_PLACES_KEY = "key=AIzaSyCUhREpmSKJMyF0ZS6EjP2FC1uhwf8dsek";
    private final int LIST_COLLEGES_API = 1;
    private final int VIEW_RIDE_AMOUNT_API = 2;
    private final int VIEW_WALLET_BALANCE_API = 3;
    private final int BOOK_RIDE_API = 4;
    private final int SEARCH_FAILED_DAILOG = 5;
    private final int CANCEL_TRIP_API = 6;
    private final int CANCEL_TRIP_DIALOG = 7;

    @BindView(R.id.autoCompleteDestination)
    CustomAutoCompleteTextView autoCompleteDestination;
    @BindView(R.id.autoCompleteLocation)
    CustomAutoCompleteTextView autoCompleteLocation;
    @BindView(R.id.cardViewRideDetails)
    CardView cardViewRideDetails;
    @BindView(R.id.cardViewMove)
    CardView cardViewMove;
    @BindView(R.id.cardViewNext)
    CardView cardViewNext;
    @BindView(R.id.cbPool)
    CheckBox cbPool;
    @BindView(R.id.layoutCurrentRider)
    View layoutCurrentRider;
//    @BindView(R.id.spinnerUniversity)
//    Spinner spinnerUniversity;
//    @BindView(R.id.spinnerSeats)
    Spinner spinnerSeats;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvMoov)
    TextView tvMoov;

    @BindView(R.id.tvRiderName)
    TextView tvRiderName;
    @BindView(R.id.imgRiderImage)
    ImageView imgRiderImage;
    @BindView(R.id.tvCarModel)
    TextView tvCarModel;
    @BindView(R.id.rating1)
    RatingView rating1;
    @BindView(R.id.tvRiderPhone)
    TextView tvRiderPhone;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvCarNumber)
    TextView tvCarNumber;
    @BindView(R.id.tvEta)
    TextView tvEta;
    @BindView(R.id.tvCancelRide)
    TextView tvCancelRide;
    @BindView(R.id.tvNoTrips)
    TextView tvNoTrips;

    @BindView(R.id.tvCarColor)
    TextView tvCarColor;

    private PlacesTask placesTask;
    private ParserTask parserTask;
    private Geocoder mGeocoder;

    private MyProgressDialog myProgressDialog;
    public ConnectionDetector cd;
    public AppPreferences appPrefes;
    public GPSTracker gpsTracker;

    private boolean isDropDownSelected = false;
    private boolean isDropDownSelectedLocation = false;
    private boolean isTypingOnDestination = true;
    private boolean isNotEnoughBalance = true;
    private int currentStep = 1;
    private String selectedCollegeId = "";
    private String currentRideId = "";

    private Double fromLat = 0.0;
    private Double fromLong = 0.0;
    private Double toLat = 0.0;
    private Double toLong = 0.0;

    public static NotificationAction notificationAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moov_fragment, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity());
        cd = new ConnectionDetector(getContext());
        appPrefes = new AppPreferences(getContext(), getResources().getString(R.string.app_name));
        gpsTracker = new GPSTracker(getContext());
        notificationAction = this;
        inItAutoCompleteLocation();
        setAutoCompleteTextViewListners();
        callViewCollegeListApi();
//        Toast.makeText(getContext(), "createView", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "resumed", Toast.LENGTH_SHORT).show();
        if (isNotEnoughBalance) {
            callViewWalletBalanceApi();
        }
    }

    private void setSeatSpinner() {
        List<String> seats = new ArrayList<>();
        seats.add("1");
        seats.add("2");
        seats.add("3");
        seats.add("4");
        seats.add("5");
        seats.add("6");
        seats.add("7");
        seats.add("8");
        WhiteSpinnerAdapter seatAdapter = new WhiteSpinnerAdapter(getActivity(), R.layout.white_spinner_list_item, R.id.title, seats);
        spinnerSeats.setAdapter(seatAdapter);
        spinnerSeats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                callViewRideCostApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.cardViewNext)
    public void cardViewNextClick() {
        if (isDropDownSelected && isDropDownSelectedLocation) {
            currentStep = 2;
            cardViewNext.setVisibility(View.GONE);
            cbPool.setVisibility(View.GONE);
            cardViewRideDetails.setVisibility(View.VISIBLE);
            cardViewMove.setVisibility(View.VISIBLE);
            setSeatSpinner();
        } else {
            Toast.makeText(getContext(), "Please choose locations", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.cardViewMove)
    public void cardViewMoveClick() {
        if (isNotEnoughBalance) {
            Intent intent = new Intent(getContext(), WalletActivity.class);
            startActivity(intent);
//            currentStep = 1;
//            cardViewNext.setVisibility(View.VISIBLE);
//            cbPool.setVisibility(View.VISIBLE);
//            cardViewRideDetails.setVisibility(View.GONE);
//            cardViewMove.setVisibility(View.GONE);
        } else {
            currentStep = 3;
//            cardViewMove.setVisibility(View.GONE);
//            cardViewRideDetails.setVisibility(View.GONE);
            callBookRideApi();
        }
    }

    @OnClick(R.id.tvCancelRide)
    public void tvCancelRideClick() {
        showAlertDialog(getContext(), "Cancel Ride", "Do you really want to cancel the ride?", "Yes", "No", CANCEL_TRIP_DIALOG);
    }

    public void inItAutoCompleteLocation() {
        autoCompleteDestination.setThreshold(1);
        autoCompleteDestination.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isTypingOnDestination = true;
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        autoCompleteLocation.setThreshold(1);
        autoCompleteLocation.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isTypingOnDestination = false;
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void setAutoCompleteTextViewListners() {
        autoCompleteDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    autoCompleteDestination.setText(autoCompleteDestination.getText().toString().substring(0, edCity.getText().toString().indexOf(",")));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    edCity.setText(edCity.getText().toString());
//                }
                autoCompleteDestination.setSelection(autoCompleteDestination.getText().toString().length());
                isDropDownSelected = true;
            }
        });
        autoCompleteDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count > after) {
                    isDropDownSelected = false;
                    currentStep = 1;
                    cardViewNext.setVisibility(View.VISIBLE);
                    cbPool.setVisibility(View.VISIBLE);
                    cardViewRideDetails.setVisibility(View.GONE);
                    cardViewMove.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    isDropDownSelected = false;
                    currentStep = 1;
                    cardViewNext.setVisibility(View.VISIBLE);
                    cbPool.setVisibility(View.VISIBLE);
                    cardViewRideDetails.setVisibility(View.GONE);
                    cardViewMove.setVisibility(View.GONE);
                }
            }
        });

        autoCompleteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    autoCompleteDestination.setText(autoCompleteDestination.getText().toString().substring(0, edCity.getText().toString().indexOf(",")));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    edCity.setText(edCity.getText().toString());
//                }
                autoCompleteLocation.setSelection(autoCompleteLocation.getText().toString().length());
                isDropDownSelectedLocation = true;
            }
        });
        autoCompleteLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count > after) {
                    isDropDownSelectedLocation = false;
                    currentStep = 1;
                    cardViewNext.setVisibility(View.VISIBLE);
                    cbPool.setVisibility(View.VISIBLE);
                    cardViewRideDetails.setVisibility(View.GONE);
                    cardViewMove.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    isDropDownSelectedLocation = false;
                    currentStep = 1;
                    cardViewNext.setVisibility(View.VISIBLE);
                    cbPool.setVisibility(View.VISIBLE);
                    cardViewRideDetails.setVisibility(View.GONE);
                    cardViewMove.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onReceveNotification(String rideId, final String title) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (title.equals("Ride Started")) {
                    tvCancelRide.setVisibility(View.GONE);
                } else {
                    currentStep = 1;
                    cardViewNext.setVisibility(View.VISIBLE);
                    cbPool.setVisibility(View.VISIBLE);
                    cardViewRideDetails.setVisibility(View.GONE);
                    cardViewMove.setVisibility(View.GONE);
                    layoutCurrentRider.setVisibility(View.GONE);
                    tvCancelRide.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    /**
     * Fetches all places from GooglePlaces AutoComplete Web Service
     */
    public class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            String data = "";
            // Obtain browser key from https://code.google.com/apis/console
            String input = "";
            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            // place type to be searched
//            String types = "types=(regions)";
            String types = "";
//            String components = "components=country:us";
            String components = "";
            // Sensor enabled
            String sensor = "sensor=false";
            // Building the parameters to the web service
            String parameters = input + "&" + types + "&" + components + "&" + sensor + "&" + GOOGLE_PLACES_KEY;
//            String parameters = input + "&" + types + "&" + sensor + "&" + GOOGLE_PLACES_KEY;
            // Output format
            String output = "json";
            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameters;
            try {
                data = downloadUrl(url);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Creating ParserTask
            parserTask = new ParserTask();
            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
            try {
                jObject = new JSONObject(jsonData[0]);
                System.out.println("json places:" + jObject);
                places = placeJsonParser.parse(jObject);
                System.out.println("placessss:" + places);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
            SimpleAdapter adapter = null;
            try {
                String[] from = new String[]{"description"};
                int[] to = new int[]{android.R.id.text1};
                adapter = new SimpleAdapter(getContext(), result, R.layout.simple_spinner_dropdown_item, from, to);
                if (isTypingOnDestination) {
                    autoCompleteDestination.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    System.out.println("atvvv" + autoCompleteDestination.getText().toString());
                    getLatLng(autoCompleteDestination.getText().toString());
                } else {
                    autoCompleteLocation.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    System.out.println("atvvv" + autoCompleteLocation.getText().toString());
                    getLatLng(autoCompleteLocation.getText().toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
//                edCityDialog.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                System.out.println("atvvv" + edCityDialog.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception while", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /**
     * Get Location lat & long
     */
    public LatLng getLatLng(String strAddress) {
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            if (isTypingOnDestination) {
                toLat = location.getLatitude();
                toLong = location.getLongitude();
            } else {
                fromLat = location.getLatitude();
                fromLong = location.getLongitude();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    /**
     * Get Location details
     *
     * @param data
     */
//    private String getCityNameByCoordinates(double lat, double lon) throws IOException {
//        mGeocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 1);
//        if (addresses != null && addresses.size() > 0) {
//            System.out.println("HAHAHA place details: country " + addresses.get(0).getCountryName() + " PostalCode " + addresses.get(0).getPostalCode() + " getLocality " + addresses.get(0).getLocality() + " getLocale " + addresses.get(0).getLocale() + " getSubLocality " + addresses.get(0).getAdminArea());
//            edCity.setText(addresses.get(0).getLocality());
//            edCity.setSelection(edCity.getText().toString().length());
//            edState.setText(addresses.get(0).getAdminArea());
//            edState.setSelection(edState.getText().toString().length());
//            edZipCode.setText(addresses.get(0).getPostalCode());
//            edZipCode.setSelection(edZipCode.getText().toString().length());
//            edCity.setError(null);
//            edState.setError(null);
//            edZipCode.setError(null);
//            try {
////                edCityDialog.setText(addresses.get(0).getLocality());
////                edCityDialog.setSelection(edCity.getText().toString().length());
////                edStateDialog.setText(addresses.get(0).getAdminArea());
////                edStateDialog.setSelection(edState.getText().toString().length());
////                edZipCodeDialog.setText(addresses.get(0).getPostalCode());
////                edZipCodeDialog.setSelection(edZipCode.getText().toString().length());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            return addresses.get(0).getLocality();
//        }
//        return null;
//    }
    private void setRiderDetails(BookRideResponseModel.DataEntity data) {
        layoutCurrentRider.setVisibility(View.VISIBLE);
        tvRiderName.setText(data.getDriver_details().getFirst_name() + " " + data.getDriver_details().getLast_name());
        tvCarModel.setText(data.getDriver_details().getCar_model());
        tvNoTrips.setText("No of trips: " + data.getDriver_details().getTotal_rides());
        rating1.setRating((int)data.getDriver_details().getRatings());
        tvRiderPhone.setText(data.getDriver_details().getPhone());
        tvDistance.setText(data.getDistance_to_drive_details().getDistance());
        tvCarNumber.setText(data.getDriver_details().getVehicle_no());
        tvEta.setText(data.getDistance_to_drive_details().getTime());
        tvCarColor.setText(data.getDriver_details().getCar_colour());
        try {
            if (data.getDriver_details().getImage().length() > 3) {
                Picasso.get().load(data.getDriver_details().getCar_image()).placeholder(R.mipmap.user_placeholder).error(R.drawable.avatar2).into(imgRiderImage);
                Picasso.get().load(data.getDriver_details().getImage()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(imgRiderImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callViewCollegeListApi() {
//        if (cd.isConnectingToInternet()) {
//            try {
//                myProgressDialog.setProgress(false);
//                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                Call<ViewCollegesResponseModel> call = apiService.viewColleges("ride/view_colleges/" + appPrefes.getData(Constants.USER_ID));
//                call.enqueue(new retrofit2.Callback<ViewCollegesResponseModel>() {
//                    @Override
//                    public void onResponse(Call<ViewCollegesResponseModel> call, Response<ViewCollegesResponseModel> response) {
//                        myProgressDialog.dismissProgress();
//                        try {
//                            if (!response.body().isStatus()) {
//                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                List<String> collegeList = new ArrayList<>();
//                                final List<String> collegeIdList = new ArrayList<>();
//                                for (int i = 0; i < response.body().getData().getDetails().size(); i++) {
//                                    collegeList.add(response.body().getData().getDetails().get(i).getName());
//                                    collegeIdList.add(response.body().getData().getDetails().get(i).getId() + "");
//                                }
//                                WhiteSpinnerAdapter collegeAdapter = new WhiteSpinnerAdapter(getActivity(), R.layout.white_spinner_list_item, R.id.title, collegeList);
//                                spinnerUniversity.setAdapter(collegeAdapter);
//                                spinnerUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        selectedCollegeId = collegeIdList.get(i);
//                                        callViewRideCostApi();
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                    }
//                                });
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            showServerErrorAlert(getContext(), LIST_COLLEGES_API);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ViewCollegesResponseModel> call, Throwable t) {
//                        myProgressDialog.dismissProgress();
//                        System.out.println("t.toString : " + t.toString());
//                        showServerErrorAlert(getContext(), LIST_COLLEGES_API);
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//                myProgressDialog.dismissProgress();
//                showServerErrorAlert(getContext(), LIST_COLLEGES_API);
//            }
//        } else {
//            showNoInternetAlert(getContext(), LIST_COLLEGES_API);
//        }
    }

    private void callViewRideCostApi() {
        if (cd.isConnectingToInternet()) {
            try {
                String poolRiding = "yes";
                if (cbPool.isChecked()) {
                    poolRiding = "yes";
                } else {
                    poolRiding = "no";
                }
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<RideSearchResponseModel> call = apiService.rideSearch("ride/search/amount/" + autoCompleteLocation.getText().toString().replaceAll(" ", "+") + "/" + autoCompleteDestination.getText().toString().replaceAll(" ", "+") + "/" + spinnerSeats.getSelectedItem() + "/" + poolRiding);
                call.enqueue(new retrofit2.Callback<RideSearchResponseModel>() {
                    @Override
                    public void onResponse(Call<RideSearchResponseModel> call, Response<RideSearchResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Log.d("Error", "onResponse: "+ response.body().getMessage());
//                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                tvAmount.setText(response.body().getData().getAmount() + "");
                                if (Double.parseDouble(response.body().getData().getAmount() + "") > Double.parseDouble(appPrefes.getData(Constants.WALLET_BALANCE))) {
                                    tvAmount.setError("Not enough balance!");
                                    tvMoov.setText("RECHARGE");
                                    isNotEnoughBalance = true;
                                } else {
                                    isNotEnoughBalance = false;
                                    tvMoov.setText("MOOV");
                                    tvAmount.setError(null);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), VIEW_RIDE_AMOUNT_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<RideSearchResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), VIEW_RIDE_AMOUNT_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), VIEW_RIDE_AMOUNT_API);
            }
        } else {
            showNoInternetAlert(getContext(), VIEW_RIDE_AMOUNT_API);
        }
    }

    private void callViewWalletBalanceApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ViewWalletBalanceResponseModel> call = apiService.viewWalletBalance("wallet/balance/" + appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<ViewWalletBalanceResponseModel>() {
                    @Override
                    public void onResponse(Call<ViewWalletBalanceResponseModel> call, Response<ViewWalletBalanceResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            appPrefes.SaveData(Constants.WALLET_BALANCE, response.body().getWallet_balance() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), VIEW_WALLET_BALANCE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewWalletBalanceResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), VIEW_WALLET_BALANCE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), VIEW_WALLET_BALANCE_API);
            }
        } else {
            showNoInternetAlert(getContext(), VIEW_WALLET_BALANCE_API);
        }
    }

    private void callBookRideApi() {
        if (cd.isConnectingToInternet()) {
            try {
                String poolRiding = "yes";
                if (cbPool.isChecked()) {
                    poolRiding = "yes";
                } else {
                    poolRiding = "no";
                }
                gpsTracker = new GPSTracker(getContext());
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<BookRideResponseModel> call = apiService.bookRide(appPrefes.getData(Constants.USER_ID), autoCompleteLocation.getText().toString().replaceAll(" ", "+"),
                        fromLat + "", fromLong + "", autoCompleteDestination.getText().toString().replaceAll(" ", "+"),
                        toLat + "", toLong + "", poolRiding, spinnerSeats.getSelectedItem().toString(), selectedCollegeId, tvAmount.getText().toString(), gpsTracker.getLatitude() + "", gpsTracker.getLongitude() + "");
                call.enqueue(new retrofit2.Callback<BookRideResponseModel>() {
                    @Override
                    public void onResponse(Call<BookRideResponseModel> call, Response<BookRideResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                setRiderDetails(response.body().getData());
                                currentRideId = response.body().getData().getRide_id() + "";
                                cardViewMove.setVisibility(View.GONE);
                                cardViewRideDetails.setVisibility(View.GONE);
                            } else {
                                showRequestSuccessDialog(getContext(), "Oops!", response.body().getMessage(), "Okay", SEARCH_FAILED_DAILOG);
                                currentStep = 1;
                                cardViewNext.setVisibility(View.VISIBLE);
                                cbPool.setVisibility(View.VISIBLE);
                                cardViewRideDetails.setVisibility(View.GONE);
                                cardViewMove.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(getContext(), BOOK_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(getContext(), BOOK_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(getContext(), BOOK_RIDE_API);
            }
        } else {
            showNoInternetAlert(getContext(), BOOK_RIDE_API);
        }
    }

    private void callCancelRideApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<CancelRideResponseModel> call = apiService.cancelRide("rides/cancel/" + currentRideId);
                call.enqueue(new retrofit2.Callback<CancelRideResponseModel>() {
                    @Override
                    public void onResponse(Call<CancelRideResponseModel> call, Response<CancelRideResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                showRequestSuccessDialog(getContext(), "Success", response.body().getMessage(), "Okay", SEARCH_FAILED_DAILOG);
                                currentRideId = "";
                                currentStep = 1;
                                cardViewNext.setVisibility(View.VISIBLE);
                                cbPool.setVisibility(View.VISIBLE);
                                layoutCurrentRider.setVisibility(View.GONE);
                                cbPool.setChecked(true);
                            } else {
                                showServerErrorAlert(getContext(), CANCEL_TRIP_API);
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
            case LIST_COLLEGES_API:
                callViewCollegeListApi();
                break;
            case VIEW_RIDE_AMOUNT_API:
                callViewRideCostApi();
                break;
            case VIEW_WALLET_BALANCE_API:
                callViewWalletBalanceApi();
                break;
            case BOOK_RIDE_API:
                callBookRideApi();
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
}
