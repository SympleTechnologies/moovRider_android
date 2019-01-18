package com.moovapp.riderapp.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.github.ornolfr.ratingview.RatingView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.moov.MoovFragment;
import com.moovapp.riderapp.main.moov.NotificationAction;
import com.moovapp.riderapp.main.paymentHistory.PaymentHistoryFragment;
import com.moovapp.riderapp.main.previousRides.PreviousRidesFragment;
import com.moovapp.riderapp.main.profile.ProfileActivity;
import com.moovapp.riderapp.main.settings.SettingsFragment;
import com.moovapp.riderapp.main.talkToUs.TalkToUsFragment;
import com.moovapp.riderapp.main.upcomingRides.UpcomingRidesFragment;
import com.moovapp.riderapp.main.wallet.WalletActivity;
import com.moovapp.riderapp.preLogin.LoginActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.GPSTracker;
import com.moovapp.riderapp.utils.LMTBaseActivity;
import com.moovapp.riderapp.utils.myGlobalFunctions.DpToPx;
import com.moovapp.riderapp.utils.myGlobalFunctions.ExpandOrCollapseViews;
import com.moovapp.riderapp.utils.placesAutocomplete.CustomAutoCompleteTextView;
import com.moovapp.riderapp.utils.placesAutocomplete.PlaceJSONParser;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.BookFutureRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.BookRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.CancelRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.RideSearchResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCollegesResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCurrentRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewProfileResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewWalletBalanceResponseModel;
import com.moovapp.riderapp.utils.spinnerAdapter.WhiteSpinnerAdapter;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Lijo Mathew Theckanal on 18-Jul-18.
 */

/**
 * Google Map Account : sympleincdevs18
 * Firebase:
 */

public class HomeActivity extends LMTBaseActivity implements HomeActivityActions, NotificationAction, OnMapReadyCallback,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final int DIALOG_LOGOUT = 11;
    private static final int ACCESS_FINE_LOCATION = 17;

    private static final String GOOGLE_PLACES_KEY = "key=AIzaSyCUhREpmSKJMyF0ZS6EjP2FC1uhwf8dsek";
    private final int LIST_COLLEGES_API = 1;
    private final int VIEW_RIDE_AMOUNT_API = 2;
    private final int VIEW_WALLET_BALANCE_API = 3;
    private final int BOOK_RIDE_API = 4;
    private final int SEARCH_FAILED_DAILOG = 5;
    private final int CANCEL_TRIP_API = 6;
    private final int CANCEL_TRIP_DIALOG = 7;
    private final int BOOK_FUTURE_RIDE_API = 8;
    private final int VIEW_CURRENT_RIDE_API = 9;
    private final int VIEW_PROFILE_API = 10;

//
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.contentView)
    FrameLayout contentView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.viewMoov)
    View viewMoov;
    @BindView(R.id.container)
    FrameLayout container;

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
    @BindView(R.id.spinnerUniversity)
    Spinner spinnerUniversity;
    @BindView(R.id.spinnerSeats)
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
    @BindView(R.id.llFutureRideDetails)
    LinearLayout llFutureRideDetails;
    @BindView(R.id.tvBookFutureRide)
    TextView tvBookFutureRide;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;

    private PlacesTask placesTask;
    private ParserTask parserTask;
    private Geocoder mGeocoder;

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

    private TextView welcomeText;
    private LinearLayout llMoovNav;
    private RelativeLayout llRidesNav;
    private LinearLayout llExpandedViewRides;
    private LinearLayout llUpcommingRidesNav;
    private LinearLayout llPreviousRidesNav;
    private LinearLayout llPaymentHistoryNav;
    private LinearLayout llTalkToUsNav;
    private LinearLayout llSettingsNav;
    private LinearLayout llLogoutNav;
    private ImageView imgRidesArrow;
    private TextView tvUserName;
    private CircleImageView profileImage;

    private boolean isExpandedViewRidesVisible = false;
    private String currentFragment = "MoovFragment";
    public static HomeActivityActions homeActivityActions;

    private GoogleMap mMap;
    private boolean isFutureRide = false;

    DatabaseReference myRef;
    Marker destinationLocationMarker;
    private boolean isDraw1stPolyLine = false;
    private int remainingTime = 10;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        setContentView(R.layout.home_activity);
        welcomeText = findViewById(R.id.welcomeText);
        ButterKnife.bind(this);
        homeActivityActions = this;
        gpsTracker = new GPSTracker(getApplicationContext());
        notificationAction = this;
        setNavigationMenus();
//        replaceFragment(new MoovFragment(), false, FragmentTransaction.TRANSIT_ENTER_MASK, "MoovFragment");
//        if (shouldAskPermission()) {
//            askPermissionLocation();
//        }
        inItAutoCompleteLocation();
        setAutoCompleteTextViewListners();
        initMap();
        callViewCollegeListApi();
        callViewCurrentRideApi();
        checkLocation();
//        callCurrentRideApi();
    }

    private void checkLocation() {
        if (gpsTracker.getLongitude() > 0) {
            fromLat = gpsTracker.getLatitude();
            fromLong = gpsTracker.getLongitude();
            try {
                getCityNameByCoordinates(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (shouldAskPermission()) {
            askPermissionLocation();
        } else {
            mMap.setMyLocationEnabled(true);
        }

        try {
            LatLng myLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tvBookFutureRide)
    public void tvBookFutureRideClick() {
        if (tvBookFutureRide.getText().toString().contains("Schedule")) {
            llFutureRideDetails.setVisibility(View.VISIBLE);
            tvBookFutureRide.setText("Book a live ride");
            isFutureRide = true;
        } else {
            llFutureRideDetails.setVisibility(View.GONE);
            tvBookFutureRide.setText("Schedule a ride");
            isFutureRide = false;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (appPrefes.getData(Constants.USER_PROFILE_PIC).length() > 3) {
                Picasso.get().load(appPrefes.getData(Constants.USER_PROFILE_PIC)).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(profileImage);
            }
            tvUserName.setText(appPrefes.getData(Constants.USER_FIRST_NAME));
            welcomeText.setText("Hey, "+ appPrefes.getData(Constants.USER_FIRST_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isNotEnoughBalance) {
            callViewWalletBalanceApi();
        }
    }

    @OnClick(R.id.cardViewWallet)
    public void cardViewWalletClick() {
        Intent intent = new Intent(HomeActivity.this, WalletActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.navMenuButton)
    public void navMenuButtonClick() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    private void setNavigationMenus() {
        setupNavigationMenu();
    }

    private void setupNavigationMenu() {
        View headerView = navigationView.getHeaderView(0);
        profileImage = (CircleImageView) headerView.findViewById(R.id.profileImage);
        tvUserName = (TextView) headerView.findViewById(R.id.tvUserName);
        llMoovNav = (LinearLayout) headerView.findViewById(R.id.llMoovNav);
        llRidesNav = (RelativeLayout) headerView.findViewById(R.id.llRidesNav);
        imgRidesArrow = (ImageView) headerView.findViewById(R.id.imgRidesArrow);
        llExpandedViewRides = (LinearLayout) headerView.findViewById(R.id.llExpandedViewRides);
        llUpcommingRidesNav = (LinearLayout) headerView.findViewById(R.id.llUpcommingRidesNav);
        llPreviousRidesNav = (LinearLayout) headerView.findViewById(R.id.llPreviousRidesNav);
        llPaymentHistoryNav = (LinearLayout) headerView.findViewById(R.id.llPaymentHistoryNav);
        llTalkToUsNav = (LinearLayout) headerView.findViewById(R.id.llTalkToUsNav);
        llSettingsNav = (LinearLayout) headerView.findViewById(R.id.llSettingsNav);
        llLogoutNav = (LinearLayout) headerView.findViewById(R.id.llLogoutNav);

        llRidesNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpandedViewRidesVisible) {
                    imgRidesArrow.setImageResource(R.mipmap.down_arrow_black);
                    isExpandedViewRidesVisible = false;
                    ExpandOrCollapseViews.collapse(llExpandedViewRides, 300, 0);
                } else {
                    imgRidesArrow.setImageResource(R.mipmap.up_arrow_black);
                    isExpandedViewRidesVisible = true;
                    ExpandOrCollapseViews.expand(llExpandedViewRides, 300, DpToPx.dp2px(81, getApplicationContext()));
                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        llMoovNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                currentFragment = "MoovFragment";
                container.setVisibility(View.GONE);
                viewMoov.setVisibility(View.VISIBLE);
                callViewCurrentRideApi();
//                delayFlow(new MoovFragment(), "MoovFragment");
                changeMenuBackgroundColor();
            }
        });
        llUpcommingRidesNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                currentFragment = "UpcomingRidesFragment";
                container.setVisibility(View.VISIBLE);
                viewMoov.setVisibility(View.GONE);
                delayFlow(new UpcomingRidesFragment(), "UpcomingRidesFragment");
                changeMenuBackgroundColor();
            }
        });
        llPreviousRidesNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                currentFragment = "PreviousRidesFragment";
                container.setVisibility(View.VISIBLE);
                viewMoov.setVisibility(View.GONE);
                delayFlow(new PreviousRidesFragment(), "PreviousRidesFragment");
                changeMenuBackgroundColor();
            }
        });
        llPaymentHistoryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                currentFragment = "PaymentHistoryFragment";
                container.setVisibility(View.VISIBLE);
                viewMoov.setVisibility(View.GONE);
                delayFlow(new PaymentHistoryFragment(), "PaymentHistoryFragment");
                changeMenuBackgroundColor();
            }
        });
        llTalkToUsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                currentFragment = "TalkToUsFragment";
                container.setVisibility(View.VISIBLE);
                viewMoov.setVisibility(View.GONE);
                delayFlow(new TalkToUsFragment(), "TalkToUsFragment");
                changeMenuBackgroundColor();
            }
        });
        llSettingsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                currentFragment = "SettingsFragment";
                container.setVisibility(View.VISIBLE);
                viewMoov.setVisibility(View.GONE);
                delayFlow(new SettingsFragment(), "SettingsFragment");
                changeMenuBackgroundColor();
            }
        });
        llLogoutNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                showAlertDialog("Logout", "Do you really want log out?", "Logout", "Cancel", DIALOG_LOGOUT);
            }
        });

    }

    private void changeMenuBackgroundColor() {
        llMoovNav.setBackgroundColor(getResources().getColor(R.color.white));
        llUpcommingRidesNav.setBackgroundColor(getResources().getColor(R.color.grayLitest));
        llPreviousRidesNav.setBackgroundColor(getResources().getColor(R.color.grayLitest));
        llPaymentHistoryNav.setBackgroundColor(getResources().getColor(R.color.white));
        llTalkToUsNav.setBackgroundColor(getResources().getColor(R.color.white));
        llSettingsNav.setBackgroundColor(getResources().getColor(R.color.white));
        switch (currentFragment) {
            case "MoovFragment":
                tvTitle.setText("Moov");
                llMoovNav.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLite));
                break;
            case "UpcomingRidesFragment":
                tvTitle.setText("Upcoming Rides");
                llUpcommingRidesNav.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLite));
                break;
            case "PreviousRidesFragment":
                tvTitle.setText("Previous Rides");
                llPreviousRidesNav.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLite));
                break;
            case "PaymentHistoryFragment":
                tvTitle.setText("Payment History");
                llPaymentHistoryNav.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLite));
                break;
            case "TalkToUsFragment":
                tvTitle.setText("Talk To Us");
                llTalkToUsNav.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLite));
                break;
            case "SettingsFragment":
                tvTitle.setText("Settings");
                llSettingsNav.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLite));
                break;
        }
    }

    //  Wait for drawer to close
    private void delayFlow(final Fragment fragment, final String name) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                replaceFragment(fragment, false, FragmentTransaction.TRANSIT_ENTER_MASK, name);
            }
        }, 330);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack,
                                 int transition, String name) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(transition);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

    @Override
    public void onProfileUpdate() {
        try {
            if (appPrefes.getData(Constants.USER_PROFILE_PIC).length() > 3) {
                Picasso.get().load(appPrefes.getData(Constants.USER_PROFILE_PIC)).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(profileImage);
            }
            welcomeText.setText("Hey, "+ appPrefes.getData(Constants.USER_FIRST_NAME));
            tvUserName.setText(appPrefes.getData(Constants.USER_FIRST_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
        callViewCollegeListApi();
    }


    private boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    private void askPermissionLocation() {
        String[] perms = {"android.permission.ACCESS_FINE_LOCATION"};
        requestPermissions(perms, ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case ACCESS_FINE_LOCATION:
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (locationAccepted) {
                    GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                    gpsTracker.getLongitude();
                    mMap.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(getBaseContext(), "Permission denied. You must allow location sharing permission to use this app!", Toast.LENGTH_SHORT).show();
                    askPermissionLocation();
                }
                break;
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
        WhiteSpinnerAdapter seatAdapter = new WhiteSpinnerAdapter(this, R.layout.white_spinner_list_item, R.id.title, seats);
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
            if (autoCompleteDestination.getText().toString().equals(autoCompleteLocation.getText().toString())) {
                autoCompleteDestination.setError("Choose a different location");
            } else {
                currentStep = 2;
                cardViewNext.setVisibility(View.GONE);
                cbPool.setVisibility(View.GONE);
                cardViewRideDetails.setVisibility(View.VISIBLE);
                cardViewMove.setVisibility(View.VISIBLE);
                tvBookFutureRide.setVisibility(View.VISIBLE);
                setSeatSpinner();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please choose locations", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.cardViewMove)
    public void cardViewMoveClick() {
        if (isNotEnoughBalance) {
            Intent intent = new Intent(getApplicationContext(), WalletActivity.class);
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
            if (isFutureRide) {
                if (tvDate.getText().toString().length() > 0 && tvTime.getText().toString().length() > 0) {
                    callBookFutureRideApi();
                } else {
                    Toast.makeText(this, "Please select a valid date and time!", Toast.LENGTH_SHORT).show();
                }
            } else {
                callBookRideApi();
            }
        }
    }

    @OnClick(R.id.tvCancelRide)
    public void tvCancelRideClick() {
        if (remainingTime < 6) {
            showAlertDialog("Cancel Ride", "Your ride is 5 minutes away, you will be charged a cancellation fee. Do you really want to cancel the ride?", "Yes", "No", CANCEL_TRIP_DIALOG);
        } else {
            showAlertDialog("Cancel Ride", "Do you really want to cancel the ride?", "Yes", "No", CANCEL_TRIP_DIALOG);
        }
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
                    tvBookFutureRide.setVisibility(View.GONE);
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
                    tvBookFutureRide.setVisibility(View.GONE);
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
                    tvBookFutureRide.setVisibility(View.GONE);
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
                    tvBookFutureRide.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onReceveNotification(String rideId, final String title) {
        this.runOnUiThread(new Runnable() {
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
                    tvBookFutureRide.setVisibility(View.GONE);
                    layoutCurrentRider.setVisibility(View.GONE);
                    tvCancelRide.setVisibility(View.VISIBLE);
                    try {
                        destinationLocationMarker.remove();
                        destinationLocationMarker = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        mMap.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        callViewCurrentRideApi();
    }

    @OnClick(R.id.tvDate)
    public void tvDateClick() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                HomeActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.tvTime)
    public void tvTimeClick() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                HomeActivity.this, true);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String mn = (monthOfYear + 1) + "";
        if (mn.length() < 2) {
            mn = "0" + mn;
        }
        String dd = dayOfMonth + "";
        if (dd.length() < 2) {
            dd = "0" + dd;
        }
        String date = year + "-" + mn + "-" + dd;
        tvDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay + ":" + minute + ":00";
        String hr = hourOfDay + "";
        String mn = minute + "";
        if (hr.length() < 2) {
            hr = "0" + hr;
        }
        if (mn.length() < 2) {
            mn = "0" + mn;
        }
        tvTime.setText(hr + ":" + mn + ":00");
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
                adapter = new SimpleAdapter(getApplicationContext(), result, R.layout.simple_spinner_dropdown_item, from, to);
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
        Geocoder coder = new Geocoder(getApplicationContext());
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
     */
    private String getCityNameByCoordinates(double lat, double lon) throws IOException {
        mGeocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 1);
        if (addresses != null && addresses.size() > 0) {
            System.out.println("HAHAHA place details: country " + addresses.get(0).getCountryName() + " PostalCode " + addresses.get(0).getPostalCode() + " getLocality " + addresses.get(0).getLocality() + " getLocale " + addresses.get(0).getLocale() + " getSubLocality " + addresses.get(0).getAdminArea());

            try {
                isDropDownSelectedLocation = true;
                autoCompleteLocation.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    autoCompleteLocation.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
                    isDropDownSelectedLocation = true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
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
            return addresses.get(0).getLocality();
        }
        return null;
    }


    private void setRiderDetails(ViewCurrentRideResponseModel.DataEntity data) {
        layoutCurrentRider.setVisibility(View.VISIBLE);
        tvRiderName.setText(data.getDriver_details().getFirst_name() + " " + data.getDriver_details().getLast_name());
        tvCarModel.setText(data.getDriver_details().getCar_model());
        tvNoTrips.setText("No of trips: ");
//        tvNoTrips.setText("No of trips: " + data.getDriver_details().getTotal_rides());
        rating1.setRating(Float.parseFloat(data.getDriver_details().getRatings() + ""));
        tvRiderPhone.setText(data.getDriver_details().getPhone());
//        tvDistance.setText(data.getDistance_to_drive_details().getDistance());
        tvDistance.setText("2 Km");
        tvCarNumber.setText(data.getDriver_details().getVehicle_no());
//        tvEta.setText(data.getDistance_to_drive_details().getTime());
        tvEta.setText("15 Min");


        try {
            if (data.getDriver_details().getImage().length() > 3) {
                Picasso.get().load(data.getDriver_details().getImage()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(imgRiderImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double lat1 = Double.parseDouble(data.getPoly_lines().getStart().getLat().substring(0, 9));
        double long1 = Double.parseDouble(data.getPoly_lines().getStart().getLng().substring(0, 9));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat1, long1)));
        List<LatLng> decodedPath = PolyUtil.decode(data.getPoly_line());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1, long1), 12));

        double lat2 = Double.parseDouble(data.getPoly_lines().getEnd().getLat().substring(0, 9));
        double long2 = Double.parseDouble(data.getPoly_lines().getEnd().getLng().substring(0, 9));

        LatLng origin = new LatLng(lat2, long2);
        LatLng destination = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        LatLngBounds bounds = new LatLngBounds.Builder().include(origin).include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));


        myRef.child(data.getDriver_details().getDriver_id() + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    Driver driver = dataSnapshot.getValue(Driver.class);
                    Log.d("HAHAHA", "Value is: " + driver);
                    showDriverOnMap(driver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HAHAHA", "Failed to read value.", error.toException());
            }
        });
    }

    private void setRiderDetails(final BookRideResponseModel.DataEntity data) {
        layoutCurrentRider.setVisibility(View.VISIBLE);
        tvRiderName.setText(data.getDriver_details().getFirst_name() + " " + data.getDriver_details().getLast_name());
        tvCarModel.setText(data.getDriver_details().getCar_model());
        tvNoTrips.setText("No of trips: " + data.getDriver_details().getTotal_rides());
        rating1.setRating(data.getDriver_details().getRatings());
        tvRiderPhone.setText(data.getDriver_details().getPhone());
        tvDistance.setText(data.getDistance_to_drive_details().getDistance());
        tvCarNumber.setText(data.getDriver_details().getVehicle_no());
        tvEta.setText(data.getDistance_to_drive_details().getTime());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LatLng destination2 = new LatLng(toLat, toLong);
                LatLng destination = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                DrawRouteMaps.getInstance(HomeActivity.this).draw(destination, destination2, mMap);
                LatLngBounds bounds1 = new LatLngBounds.Builder().include(destination).include(destination2).build();
                Point displaySize1 = new Point();
                getWindowManager().getDefaultDisplay().getSize(displaySize1);
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds1, displaySize1.x, 250, 30));
            }
        }, 5000);

        try {
            if (data.getDriver_details().getImage().length() > 3) {
                Picasso.get().load(data.getDriver_details().getImage()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(imgRiderImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        myRef.child(data.getDriver_details().getDriver_id() + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    Driver driver = dataSnapshot.getValue(Driver.class);
                    Log.d("HAHAHA", "Value is: " + driver);
                    showDriverOnMap(driver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HAHAHA", "Failed to read value.", error.toException());
            }
        });
    }

    int tt = 0;

    private void showDriverOnMap(Driver driver) {
        try {
            if (destinationLocationMarker == null) {
                destinationLocationMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt())))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_car_icon)));
                destinationLocationMarker.setRotation(Float.parseFloat(driver.getAngleX() + ""));
            } else {
                destinationLocationMarker.setPosition(new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt())));
                destinationLocationMarker.setRotation(Float.parseFloat(driver.getAngleX() + ""));
            }
            if (!isDraw1stPolyLine) {
                try {
                    LatLng origin = new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt()));
                    LatLng destination = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    DrawRouteMaps.getInstance(this).draw(origin, destination, mMap);
                    LatLngBounds bounds = new LatLngBounds.Builder().include(origin).include(destination).build();
                    Point displaySize = new Point();
                    getWindowManager().getDefaultDisplay().getSize(displaySize);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LatLng destination2 = new LatLng(toLat, toLong);
                            LatLng destination = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                            DrawRouteMaps.getInstance(HomeActivity.this).draw(destination, destination2, mMap);
                            LatLngBounds bounds1 = new LatLngBounds.Builder().include(destination).include(destination2).build();
                            Point displaySize1 = new Point();
                            getWindowManager().getDefaultDisplay().getSize(displaySize1);
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds1, displaySize1.x, 250, 30));
                        }
                    }, 5000);

                    isDraw1stPolyLine = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tt == 0) {
                try {
                    Location location1 = new Location("");
                    location1.setLatitude(gpsTracker.getLatitude());
                    location1.setLongitude(gpsTracker.getLongitude());
                    Location location2 = new Location("");
                    location2.setLatitude(Double.parseDouble(driver.getLat()));
                    location2.setLongitude(Double.parseDouble(driver.getLongt()));
                    float distanceInMeters = location1.distanceTo(location2);
                    System.out.println("HAHAHAHA: " + distanceInMeters);
                    remainingTime = (int) ((distanceInMeters / 650));
                    tvEta.setText(((int) ((distanceInMeters / 650)) + 1) + " Min");
                    tvDistance.setText(((int) (distanceInMeters / 1000)) + " Km");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tt++;
                if (tt == 5) {
                    tt = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callViewCollegeListApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ViewCollegesResponseModel> call = apiService.viewColleges("ride/view_colleges/" + appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<ViewCollegesResponseModel>() {
                    @Override
                    public void onResponse(Call<ViewCollegesResponseModel> call, Response<ViewCollegesResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                List<String> collegeList = new ArrayList<>();
                                int p = 0;
                                final List<String> collegeIdList = new ArrayList<>();
                                for (int i = 0; i < response.body().getData().getDetails().size(); i++) {
                                    collegeList.add(response.body().getData().getDetails().get(i).getName());
                                    collegeIdList.add(response.body().getData().getDetails().get(i).getId() + "");
                                    if (response.body().getData().getUser_institute() == response.body().getData().getDetails().get(i).getId()) {
                                        p = i;
                                    }
                                }
                                WhiteSpinnerAdapter collegeAdapter = new WhiteSpinnerAdapter(HomeActivity.this, R.layout.white_spinner_list_item, R.id.title, collegeList);
                                spinnerUniversity.setAdapter(collegeAdapter);
                                spinnerUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        selectedCollegeId = collegeIdList.get(i);
                                        callViewRideCostApi();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                try {
                                    spinnerUniversity.setSelection(p);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(LIST_COLLEGES_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewCollegesResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(LIST_COLLEGES_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(LIST_COLLEGES_API);
            }
        } else {
            showNoInternetAlert(LIST_COLLEGES_API);
        }
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
                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                            showServerErrorAlert(VIEW_RIDE_AMOUNT_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<RideSearchResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_RIDE_AMOUNT_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(VIEW_RIDE_AMOUNT_API);
            }
        } else {
            showNoInternetAlert(VIEW_RIDE_AMOUNT_API);
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
                            showServerErrorAlert(VIEW_WALLET_BALANCE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewWalletBalanceResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_WALLET_BALANCE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(VIEW_WALLET_BALANCE_API);
            }
        } else {
            showNoInternetAlert(VIEW_WALLET_BALANCE_API);
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
                gpsTracker = new GPSTracker(getApplicationContext());
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
                                tvBookFutureRide.setVisibility(View.GONE);
                                cardViewRideDetails.setVisibility(View.GONE);

                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(fromLat, fromLong)));

                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(toLat, toLong)));
                                List<LatLng> decodedPath = PolyUtil.decode(response.body().getPoly_line());
                                mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(fromLat, fromLong), 12));

                            } else {
                                showRequestSuccessDialog("Oops!", response.body().getMessage(), "Okay", SEARCH_FAILED_DAILOG);
                                currentStep = 1;
                                cardViewNext.setVisibility(View.VISIBLE);
                                cbPool.setVisibility(View.VISIBLE);
                                cardViewRideDetails.setVisibility(View.GONE);
                                cardViewMove.setVisibility(View.GONE);
                                tvBookFutureRide.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(BOOK_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(BOOK_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(BOOK_RIDE_API);
            }
        } else {
            showNoInternetAlert(BOOK_RIDE_API);
        }
    }

    private void callBookFutureRideApi() {
        if (cd.isConnectingToInternet()) {
            try {
                String poolRiding = "yes";
                if (cbPool.isChecked()) {
                    poolRiding = "yes";
                } else {
                    poolRiding = "no";
                }
                gpsTracker = new GPSTracker(getApplicationContext());
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<BookFutureRideResponseModel> call = apiService.bookFutureRide(appPrefes.getData(Constants.USER_ID), autoCompleteLocation.getText().toString().replaceAll(" ", "+"),
                        fromLat + "", fromLong + "", autoCompleteDestination.getText().toString().replaceAll(" ", "+"),
                        toLat + "", toLong + "", poolRiding, spinnerSeats.getSelectedItem().toString(), selectedCollegeId, tvAmount.getText().toString(), gpsTracker.getLatitude() + "", gpsTracker.getLongitude() + ""
                        , tvDate.getText().toString(), tvTime.getText().toString());
                call.enqueue(new retrofit2.Callback<BookFutureRideResponseModel>() {
                    @Override
                    public void onResponse(Call<BookFutureRideResponseModel> call, Response<BookFutureRideResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                cardViewMove.setVisibility(View.GONE);
                                tvBookFutureRide.setVisibility(View.GONE);
                                cardViewRideDetails.setVisibility(View.GONE);
                                currentStep = 1;
                                cardViewNext.setVisibility(View.VISIBLE);
                                cbPool.setVisibility(View.VISIBLE);
                                cardViewRideDetails.setVisibility(View.GONE);
                                cardViewMove.setVisibility(View.GONE);
                                tvBookFutureRide.setVisibility(View.GONE);
                                tvDate.setText("");
                                tvTime.setText("");
                                tvBookFutureRide.setText("Schedule a ride");
                                llFutureRideDetails.setVisibility(View.GONE);
                                Toast.makeText(HomeActivity.this, "Ride booked!", Toast.LENGTH_SHORT).show();
                            } else {
                                showRequestSuccessDialog("Oops!", response.body().getMessage(), "Okay", SEARCH_FAILED_DAILOG);
                                currentStep = 1;
                                cardViewNext.setVisibility(View.VISIBLE);
                                cbPool.setVisibility(View.VISIBLE);
                                cardViewRideDetails.setVisibility(View.GONE);
                                cardViewMove.setVisibility(View.GONE);
                                tvBookFutureRide.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(BOOK_FUTURE_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookFutureRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(BOOK_FUTURE_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(BOOK_FUTURE_RIDE_API);
            }
        } else {
            showNoInternetAlert(BOOK_FUTURE_RIDE_API);
        }
    }

    private void callCancelRideApi() {
        if (cd.isConnectingToInternet()) {
            try {
                String freeOrPaid = "free";
                if (remainingTime < 6) {
                    freeOrPaid = "paid";
                }
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<CancelRideResponseModel> call = apiService.cancelRide("rides/cancel/" + currentRideId + "/" + freeOrPaid);
                call.enqueue(new retrofit2.Callback<CancelRideResponseModel>() {
                    @Override
                    public void onResponse(Call<CancelRideResponseModel> call, Response<CancelRideResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                showRequestSuccessDialog("Success", response.body().getMessage(), "Okay", SEARCH_FAILED_DAILOG);
                                currentRideId = "";
                                currentStep = 1;
                                cardViewNext.setVisibility(View.VISIBLE);
                                cbPool.setVisibility(View.VISIBLE);
                                layoutCurrentRider.setVisibility(View.GONE);
                                isDraw1stPolyLine = false;
                                mMap.clear();
                                try {
                                    destinationLocationMarker.remove();
                                    destinationLocationMarker = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                cbPool.setChecked(true);
                            } else {
                                showServerErrorAlert(CANCEL_TRIP_API);
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
                        try {
                            if (response.body().isStatus()) {
                                boolean isLiveRide = false;
                                for (int i = 0; i < response.body().getData().size(); i++) {
                                    if (response.body().getData().get(i).getRide_type().equals("live")) {
                                        setRiderDetails(response.body().getData().get(i));
                                        currentRideId = response.body().getData().get(i).getRide_id() + "";
                                        cardViewMove.setVisibility(View.GONE);
                                        tvBookFutureRide.setVisibility(View.GONE);
                                        cardViewRideDetails.setVisibility(View.GONE);
                                        cardViewNext.setVisibility(View.GONE);
                                        cbPool.setVisibility(View.GONE);
                                        toLat = Double.parseDouble(response.body().getData().get(i).getPoly_lines().getEnd().getLat().substring(0, 9));
                                        toLong = Double.parseDouble(response.body().getData().get(i).getPoly_lines().getEnd().getLng().substring(0, 9));
                                    }
                                    if (response.body().getData().get(i).getRide_type().equals("live")) {
                                        isLiveRide = true;
                                    }
                                }
                                if (!isLiveRide) {
                                    currentRideId = "";
                                    currentStep = 1;
                                    cardViewNext.setVisibility(View.VISIBLE);
                                    cbPool.setVisibility(View.VISIBLE);
                                    layoutCurrentRider.setVisibility(View.GONE);
                                    cardViewMove.setVisibility(View.GONE);
                                    tvBookFutureRide.setVisibility(View.GONE);
                                    cardViewRideDetails.setVisibility(View.GONE);
                                    try {
                                        destinationLocationMarker.remove();
                                        destinationLocationMarker = null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    cbPool.setChecked(true);
                                }
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(VIEW_CURRENT_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewCurrentRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_CURRENT_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(VIEW_CURRENT_RIDE_API);
            }
        } else {
            showNoInternetAlert(VIEW_CURRENT_RIDE_API);
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
            case BOOK_FUTURE_RIDE_API:
                callBookFutureRideApi();
                break;
            case CANCEL_TRIP_API:
                callCancelRideApi();
                break;
            case VIEW_CURRENT_RIDE_API:
                callViewCurrentRideApi();
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
            case DIALOG_LOGOUT:
                appPrefes.clearData();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
