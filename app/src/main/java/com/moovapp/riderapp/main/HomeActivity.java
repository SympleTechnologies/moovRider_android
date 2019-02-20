package com.moovapp.riderapp.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.TripsFragment;
import com.moovapp.riderapp.main.adapters.LocationAdapter;
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
import com.moovapp.riderapp.utils.LatLngInterpolator;
import com.moovapp.riderapp.utils.MarkerAnimation;
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
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewWalletBalanceResponseModel;
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

    @BindView(R.id.cardLocations)
    CardView cardLocations;

    @BindView(R.id.searchResultsTv)
    TextView searchResultsTv;

    @BindView(R.id.searchResults)
    ScrollView searchResults;

    @BindView(R.id.autoCompleteDestination)
    CustomAutoCompleteTextView autoCompleteDestination;
    @BindView(R.id.autoCompleteLocation)
    CustomAutoCompleteTextView autoCompleteLocation;
    @BindView(R.id.cardViewRideDetails)
    LinearLayout cardViewRideDetails;
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
//    Spinner spinnerSeats;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvMoov)
    TextView tvMoov;

    @BindView(R.id.searchDestination)
    ImageView searchDestination;

    @BindView(R.id.tvRiderName)
    TextView tvRiderName;
    @BindView(R.id.imgRiderImage)
    ImageView imgRiderImage;
    @BindView(R.id.tvCarModel)
    TextView tvCarModel;
//    @BindView(R.id.rating1)
//    RatingView rating1;
//    @BindView(R.id.tvRiderPhone)
//    TextView tvRiderPhone;
//    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvCarNumber)
    TextView tvCarNumber;
    @BindView(R.id.tvEta)
    TextView tvEta;
    @BindView(R.id.tvCancelRide)
    TextView tvCancelRide;
//    @BindView(R.id.tvNoTrips)
//    TextView tvNoTrips;
    @BindView(R.id.llFutureRideDetails)
    LinearLayout llFutureRideDetails;
    @BindView(R.id.tvBookFutureRide)
    TextView tvBookFutureRide;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvDestinationName)
    TextView tvDestinationName;
    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.changeDestination)
    ImageView changeDestination;
//    @BindView(R.id.changeLocation)
//    ImageView changeLocation;
    @BindView(R.id.scrollViewResults)
    LinearLayout scrollViewResults;



    public FirebaseAuth mAuth;
    private PlacesTask placesTask;
    private ParserTask parserTask;
    private Geocoder mGeocoder;

    private LatLng driverOldLocation;

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
    private LinearLayout llRidesNav;
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
    public RecyclerView recyclerViewLocation;
    public LocationAdapter locationAdapter;
    public CardView locations;
    public Button increaseSeats;
    public Button decreaseSeats;
    public int seatNumber = 1;
    public TextView tvSeatNumber;
    public EditText goingTo;
    private LinearLayout location;
    private SensorManager mySensorManager;
//    public TextView tvSearch;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        setContentView(R.layout.home_activity);
        location = findViewById(R.id.location);
        goingTo = findViewById(R.id.goingTo);
        tvSeatNumber = findViewById(R.id.tvSeatNumber);
        recyclerViewLocation = findViewById(R.id.recycler_location);
        DividerItemDecoration divider = new
                DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(
                ContextCompat.getDrawable(this, R.drawable.line_divider)
        );
        recyclerViewLocation.addItemDecoration(divider);
        increaseSeats = findViewById(R.id.increaseSeats);
        decreaseSeats = findViewById(R.id.decreaseSeats);
        locations = findViewById(R.id.locations);
//        tvSearch = findViewById(R.id.tvSearch);
        recyclerViewLocation.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLocation.setHasFixedSize(true);
        welcomeText = findViewById(R.id.welcomeText);
        ButterKnife.bind(this);
        homeActivityActions = this;
        gpsTracker = new GPSTracker(getApplicationContext());
        notificationAction = this;
        goingTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                    scrollViewResults.setVisibility(View.VISIBLE);
                    location.setVisibility(View.GONE);
                    searchResultsTv.setVisibility(View.GONE);
                    searchResults.setVisibility(View.GONE);
                    autoCompleteLocation.clearFocus();
                    autoCompleteDestination.requestFocus();
                    currentStep = 9;
                }
            }
        });
        goingTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                scrollViewResults.setVisibility(View.VISIBLE);
                location.setVisibility(View.GONE);
                searchResultsTv.setVisibility(View.GONE);
                searchResults.setVisibility(View.GONE);
                autoCompleteLocation.clearFocus();
                autoCompleteDestination.requestFocus();
                currentStep = 9;
            }
        });
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



//
    @OnClick(R.id.searchDestination)
    public void searchDestination(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        scrollViewResults.setVisibility(View.VISIBLE);
        location.setVisibility(View.GONE);
        searchResultsTv.setVisibility(View.GONE);
        searchResults.setVisibility(View.GONE);
        autoCompleteLocation.clearFocus();
        autoCompleteDestination.requestFocus();
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
            welcomeText.setText("Hey "+ appPrefes.getData(Constants.USER_FIRST_NAME));
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
        llRidesNav = (LinearLayout) headerView.findViewById(R.id.llRidesNav);
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
                String upcoming = "previous";
//                Intent i = new Intent(HomeActivity.this, TripsFragment.class);
//                i.putExtra("firstTab", upcoming);
//                startActivity(i);
                Bundle args = new Bundle();
                args.putString("firstTab", upcoming);
                TripsFragment tripsFragment = new TripsFragment();
                tripsFragment.setArguments(args);
                delayFlow(tripsFragment, "UpcomingRidesFragment");
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
                String upcoming = "previous";
                Bundle args = new Bundle();
                args.putString("firstTab", upcoming);
                TripsFragment tripsFragment = new TripsFragment();
                tripsFragment.setArguments(args);
                delayFlow(tripsFragment, "PreviousRidesFragment");
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

    private void setUpSeatListeners(){
//        callViewRideCostApi();
        increaseSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seatNumber == 7){
                    return;
                }
                seatNumber++;
                tvSeatNumber.setText(String.valueOf(seatNumber));

            }
        });

        decreaseSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seatNumber < 2){
                    return;
                }
                seatNumber--;
                tvSeatNumber.setText(String.valueOf(seatNumber));
            }
        });

        tvSeatNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                callViewRideCostApi();
            }
        });
    }


    private void setSeatSpinner() {
//        setUpSeatListeners();
        callViewRideCostApi();
//        List<String> seats = new ArrayList<>();
//        seats.add("1");
//        seats.add("2");
//        seats.add("3");
//        seats.add("4");
//        seats.add("5");
//        seats.add("6");
//        seats.add("7");
//        seats.add("8");
//        WhiteSpinnerAdapter seatAdapter = new WhiteSpinnerAdapter(this, R.layout.white_spinner_list_item, R.id.title, seats);
//        spinnerSeats.setAdapter(seatAdapter);
//        spinnerSeats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                callViewRideCostApi();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (currentStep == 2){
            cardViewNext.setVisibility(View.VISIBLE);
            cbPool.setVisibility(View.VISIBLE);
            cardViewRideDetails.setVisibility(View.GONE);
            cardViewMove.setVisibility(View.GONE);
            tvBookFutureRide.setVisibility(View.GONE);
        }if (currentStep == 3){
            locations.setVisibility(View.VISIBLE);
            cardViewNext.setVisibility(View.GONE);
            cbPool.setVisibility(View.GONE);
            cardViewRideDetails.setVisibility(View.VISIBLE);
            cardViewMove.setVisibility(View.VISIBLE);
            tvBookFutureRide.setVisibility(View.VISIBLE);
            locations.setVisibility(View.GONE);
            tvLocationName.setText(autoCompleteLocation.getText().toString());
            tvDestinationName.setText(autoCompleteDestination.getText().toString());
            setSeatSpinner();
            setUpSeatListeners();
        }if (currentStep == 9){
            scrollViewResults.setVisibility(View.GONE);
            location.setVisibility(View.VISIBLE);
            searchResultsTv.setVisibility(View.GONE);
            searchResults.setVisibility(View.GONE);
            currentStep = 0;
        }

//        super.onBackPressed();

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
                locations.setVisibility(View.GONE);
                tvLocationName.setText(autoCompleteLocation.getText().toString());
                tvDestinationName.setText(autoCompleteDestination.getText().toString());
                setSeatSpinner();
                setUpSeatListeners();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please choose locations", Toast.LENGTH_SHORT).show();
        }
    }


//    @OnClick(R.id.changeLocation)
//    public void changeLocationClick(){
//        cardViewRideDetails.setVisibility(View.GONE);
//        cardLocations.setVisibility(View.VISIBLE);
//        scrollViewResults.setVisibility(View.VISIBLE);
////        locations.setVisibility(View.VISIBLE);
////        location.setVisibility(View.GONE);
//
//    }

    @OnClick(R.id.changeDestination)
    public void changeDestinationClick(){
        cardViewRideDetails.setVisibility(View.GONE);
        cardLocations.setVisibility(View.VISIBLE);
        scrollViewResults.setVisibility(View.VISIBLE);
//        locations.setVisibility(View.VISIBLE);
//        location.setVisibility(View.GONE);
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
            locations.setVisibility(View.GONE);
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
            if(cancelledTrip){
                locations.setVisibility(View.VISIBLE);
            }
        }

    }

    public void inItAutoCompleteLocation() {
        autoCompleteDestination.setThreshold(1);
        autoCompleteDestination.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                location.setVisibility(View.GONE);
//                Toast.makeText(HomeActivity.this, "changed", Toast.LENGTH_SHORT).show();
                searchResultsTv.setVisibility(View.VISIBLE);
                searchResults.setVisibility(View.VISIBLE);
                if (s.length() > 3){
                    scrollViewResults.setBackgroundColor(getResources().getColor(R.color.semi_transparent));
//                    Toast.makeText(HomeActivity.this, "3 and above", Toast.LENGTH_SHORT).show();
                    isTypingOnDestination = true;
                    placesTask = new PlacesTask();
                    placesTask.execute(s.toString());
                }

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
//                location.setVisibility(View.GONE);
                searchResultsTv.setVisibility(View.VISIBLE);
                searchResults.setVisibility(View.VISIBLE);
//                Toast.makeText(HomeActivity.this, "location", Toast.LENGTH_SHORT).show();
                    if (s.length() > 3){
                        scrollViewResults.setBackgroundColor(getResources().getColor(R.color.semi_transparent));
//                        Toast.makeText(HomeActivity.this, "3 and above", Toast.LENGTH_SHORT).show();
                        isTypingOnDestination = false;
                        placesTask = new PlacesTask();
                        placesTask.execute(s.toString());
                    }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        autoCompleteLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    recyclerViewLocation.setVisibility(View.GONE);
                    scrollViewResults.setVisibility(View.GONE);
                }else{
                    recyclerViewLocation.setVisibility(View.VISIBLE);
                    scrollViewResults.setVisibility(View.VISIBLE);

                }
            }
        });

        autoCompleteDestination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    recyclerViewLocation.setVisibility(View.GONE);
                    scrollViewResults.setVisibility(View.GONE);
                }else{
                    recyclerViewLocation.setVisibility(View.VISIBLE);
                    scrollViewResults.setVisibility(View.VISIBLE);
                }
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
//                    recyclerViewLocation.setVisibility(View.GONE);
//                    tvSearch.setVisibility(View.GONE);
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
//                    recyclerViewLocation.setVisibility(View.VISIBLE);
//                    tvSearch.setVisibility(View.VISIBLE);
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
//                    recyclerViewLocation.setVisibility(View.GONE);
//                    tvSearch.setVisibility(View.GONE);
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
//                    recyclerViewLocation.setVisibility(View.VISIBLE);
//                    tvSearch.setVisibility(View.VISIBLE);
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
//                    recyclerViewLocation.setVisibility(View.VISIBLE);
//                    autoCompleteDestination.setAdapter(adapter);
                    locationAdapter = new LocationAdapter(result, new LocationAdapter.ListItemClickListener() {
                        @Override
                        public void OnLocationClicked(HashMap<String, String> selectedLocation) {
                            autoCompleteDestination.setText(selectedLocation.get("description"));
                            recyclerViewLocation.setVisibility(View.GONE);
                            scrollViewResults.setBackgroundColor(getResources().getColor(R.color.transparent));
                            recyclerViewLocation.setVisibility(View.GONE);
                            isDropDownSelected = true;
                            isDropDownSelectedLocation = true;
                            searchResultsTv.setVisibility(View.GONE);
                            searchResults.setVisibility(View.GONE);
                            cardLocations.setVisibility(View.GONE);


//                            new stuff
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(autoCompleteDestination.getWindowToken(),0);
                            currentStep = 2;
                            cardViewNext.setVisibility(View.GONE);
                            cbPool.setVisibility(View.GONE);
                            cardViewRideDetails.setVisibility(View.VISIBLE);
                            cardViewMove.setVisibility(View.VISIBLE);
                            tvBookFutureRide.setVisibility(View.VISIBLE);
                            locations.setVisibility(View.GONE);
                            tvLocationName.setText(autoCompleteLocation.getText().toString());
                            tvDestinationName.setText(autoCompleteDestination.getText().toString());
                            setSeatSpinner();
                            setUpSeatListeners();
                            scrollViewResults.setVisibility(View.GONE);
//                            tvSearch.setVisibility(View.GONE);
                        }
                    });
//                    recyclerViewLocation.setVisibility(View.VISIBLE);
                    recyclerViewLocation.setAdapter(locationAdapter);
                    locationAdapter.notifyDataSetChanged();
//                    Log.d("onPostExecute", "onPostExecute: "+ result.get(0).toString());
//                    recyclerViewLocation.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                    System.out.println("atvvv" + autoCompleteDestination.getText().toString());
                    getLatLng(autoCompleteDestination.getText().toString());
                } else {
//                    recyclerViewLocation.setVisibility(View.VISIBLE);
                    locationAdapter = new LocationAdapter(result, new LocationAdapter.ListItemClickListener() {
                        @Override
                        public void OnLocationClicked(HashMap<String, String> selectedLocation) {
                            autoCompleteLocation.setText(selectedLocation.get("description"));
                            isDropDownSelected = true;
                            isDropDownSelectedLocation = true;
                            recyclerViewLocation.setVisibility(View.GONE);
//                            searchResultsTv.setVisibility(View.GONE);
//                            searchResults.setVisibility(View.GONE);
                            scrollViewResults.setBackgroundColor(getResources().getColor(R.color.transparent));
//                            scrollViewResults.setVisibility(View.GONE);
//                            tvSearch.setVisibility(View.GONE);
                            searchResultsTv.setVisibility(View.GONE);
                            searchResults.setVisibility(View.GONE);
                            if (!TextUtils.isEmpty(autoCompleteDestination.getText().toString())){
                                currentStep = 2;
                                cardViewNext.setVisibility(View.GONE);
                                cbPool.setVisibility(View.GONE);
                                cardViewRideDetails.setVisibility(View.VISIBLE);
                                cardViewMove.setVisibility(View.VISIBLE);
                                tvBookFutureRide.setVisibility(View.VISIBLE);
                                locations.setVisibility(View.GONE);
                                tvLocationName.setText(autoCompleteLocation.getText().toString());
                                tvDestinationName.setText(autoCompleteDestination.getText().toString());
                                setSeatSpinner();
                                setUpSeatListeners();
                                scrollViewResults.setVisibility(View.GONE);
                            }

                        }
                    });

                    recyclerViewLocation.setAdapter(locationAdapter);
                    locationAdapter.notifyDataSetChanged();
//                    autoCompleteLocation.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
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
                tvLocationName.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
                autoCompleteLocation.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    tvLocationName.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
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


    private void setRiderDetails(final ViewCurrentRideResponseModel.DataEntity data) {
        layoutCurrentRider.setVisibility(View.VISIBLE);
        tvRiderName.setText(data.getDriverDetails().getFirstName() + " " + data.getDriverDetails().getLastName());
//        tvCarModel.setText(data.getDriverDetails().getCarModel());
//        tvNoTrips.setText("No of trips: ");
//        tvNoTrips.setText("No of trips: " + data.getDriver_details().getTotal_rides());
//        rating1.setRating(Float.parseFloat(data.getDriverDetails().getRatings() + ""));
//        tvRiderPhone.setText(data.getDriverDetails().getPhone());
//        tvDistance.setText(data.getDistance_to_drive_details().getDistance());
//        tvDistance.setText("2 Km");
        tvCarNumber.setText(data.getDriverDetails().getVehicleNo());
//        Toast.makeText(this, "details", Toast.LENGTH_SHORT).show();
//        Log.e("response", "setRiderDetails: "+data.getDriveDetais().getTime().toString() );
//        tvEta.setText(String.valueOf(data.getDriveDetais().getDistance()));
//        tvEta.setText("15 Min");

//        try {
//            if (data.getDriverDetails().getImage().length() > 3) {
//                Picasso.get().load(data.getDriverDetails().getImage()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(imgRiderImage);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        double lat1 = Double.parseDouble(data.getPolyLines().getStart().getLat().toString().substring(0, data.getPolyLines().getStart().getLat().toString().length()-1));
        double long1 = Double.parseDouble(data.getPolyLines().getStart().getLng().toString().substring(0, data.getPolyLines().getStart().getLng().toString().length()-1));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat1, long1)));
        List<LatLng> decodedPath = PolyUtil.decode(data.getPolyLine());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1, long1), 12));

        double lat2 = Double.parseDouble(data.getPolyLines().getEnd().getLat().toString().substring(0, data.getPolyLines().getEnd().getLat().toString().length()-1));
        double long2 = Double.parseDouble(data.getPolyLines().getEnd().getLng().toString().substring(0, data.getPolyLines().getEnd().getLng().toString().length()-1));

        LatLng origin = new LatLng(lat2, long2);
        LatLng destination = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        LatLngBounds bounds = new LatLngBounds.Builder().include(origin).include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
//        Log.d("HAHAHA", "Value is: " + data.getDriverDetails().getDriverId());

        myRef.child("49").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
//                    long lat = dataSnapshot.getChildrenCount();
                    float angleX = dataSnapshot.child("angleX").getValue(float.class);
                    Double lat = dataSnapshot.child("lat").getValue(Double.class);
                    Double longt = dataSnapshot.child("longt").getValue(Double.class);
                    Driver driver = new Driver();
                    driver.setAngleX(angleX);
                    driver.setLat(String.valueOf(lat));
                    driver.setLongt(String.valueOf(longt));
                    driver.setId(data.getDriverDetails().getDriverId());

//                    Driver driver =(Driver) dataSnapshot.getValue(Driver.class);
                    Log.d("HAHAHA", "Value is: " + angleX);
                    Log.d("HAHAHA", "Value is: " + lat);
                    Log.d("HAHAHA", "Value is: " + longt);
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
//        tvNoTrips.setText("No of trips: " + data.getDriver_details().getTotal_rides());
//        rating1.setRating(data.getDriver_details().getRatings());
//        tvRiderPhone.setText(data.getDriver_details().getPhone());
//        tvDistance.setText(data.getDistance_to_drive_details().getDistance());
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

    public static Bitmap getBItmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);


        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();

        } else if (drawable instanceof VectorDrawableCompat || drawable instanceof VectorDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    private float bearingBetweenLocations(LatLng latLng1,LatLng latLng2) {

        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        return (float) brng;
//        return brng;
    }

    boolean isMarkerRotating;

    private void rotateMarker(final Marker marker, final float toRotation) {
        if(!isMarkerRotating) {
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final float startRotation = marker.getRotation();
            final long duration = 1000;

            final Interpolator interpolator = new LinearInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    isMarkerRotating = true;

                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed / duration);

                    float rot = t * toRotation + (1 - t) * startRotation;

                    marker.setRotation(-rot > 180 ? rot / 2 : rot);
                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    } else {
                        isMarkerRotating = false;
                    }
                }
            });
        }
    }

    public float getAngle(LatLng comimgFrom, LatLng goingTo){

//        com.google.maps.android.geometry.Point point = new com.google.maps.android.geometry.Point(comimgFrom.latitude, comimgFrom.longitude);

        float angle =  (float) Math.toDegrees(Math.atan2(goingTo.longitude - comimgFrom.longitude , goingTo.latitude - comimgFrom.latitude));

        if (angle < 0){
            angle += 360;
        }

        return angle;
    }

    private void showDriverOnMap(Driver driver) {
        try {
            Log.d("dest", "showDriverOnMap: "+ String.valueOf(driver.getLat()) + ",,, " +  String.valueOf(driver.getLongt()));
            if (destinationLocationMarker == null) {

//
//                destinationLocationMarker = mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt())))
//                        .icon(BitmapDescriptorFactory.fromBitmap(getBItmapFromDrawable(HomeActivity.this, R.drawable.map_car_icon_new))));
//                destinationLocationMarker.setRotation(Float.parseFloat(driver.getAngleX() + ""));
                driverOldLocation = new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt()));
                MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getBItmapFromDrawable(this, R.drawable.map_car_icon_new)));
                markerOptions.position(new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt())));
////        markerOptions.draggable(true);
////        markerOptions.title("Current Position");
////        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                destinationLocationMarker = mMap.addMarker(markerOptions);
                destinationLocationMarker.setFlat(true);
//                rotateMarker(destinationLocationMarker);
//                destinationLocationMarker.setRotation(Float.parseFloat(driver.getAngleX() + ""));

            } else {
                LatLng driverPosition = new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt()));
//                float rotationAngleX = bearingBetweenLocations(driverOldLocation, driverPosition);
//                rotateMarker(destinationLocationMarker,rotationAngleX);
                MarkerAnimation.animateMarkerToGB(destinationLocationMarker, driverPosition, new LatLngInterpolator.Spherical());
                destinationLocationMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getBItmapFromDrawable(this, R.drawable.map_car_icon_new)));
                float rotationAngleX = getAngle(driverOldLocation, driverPosition);
                destinationLocationMarker.setFlat(true);
                destinationLocationMarker.setRotation(rotationAngleX);
                Toast.makeText(this, ""+rotationAngleX, Toast.LENGTH_SHORT).show();
                Log.d("rotate", "showDriverOnMap: "+ rotationAngleX);
                Toast.makeText(this, "mmovement", Toast.LENGTH_SHORT).show();
//                destinationLocationMarkermarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(getBItmapFromDrawable(this, R.drawable.map_car_icon_new)));
//                destinationLocationMarker.setPosition(new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLongt())));
//                destinationLocationMarker.setRotation(Float.parseFloat(driver.getAngleX() + ""));
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
            if (tt < 5) {
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
//                    tvDistance.setText(((int) (distanceInMeters / 1000)) + " Km");
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
//                                List<String> collegeList = new ArrayList<>();
//                                int p = 0;
//                                final List<String> collegeIdList = new ArrayList<>();
//                                for (int i = 0; i < response.body().getData().getDetails().size(); i++) {
//                                    collegeList.add(response.body().getData().getDetails().get(i).getName());
//                                    collegeIdList.add(response.body().getData().getDetails().get(i).getId() + "");
//                                    if (response.body().getData().getUser_institute() == response.body().getData().getDetails().get(i).getId()) {
//                                        p = i;
//                                    }
//                                }
//                                WhiteSpinnerAdapter collegeAdapter = new WhiteSpinnerAdapter(HomeActivity.this, R.layout.white_spinner_list_item, R.id.title, collegeList);
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
//                                try {
//                                    spinnerUniversity.setSelection(p);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
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
                Call<RideSearchResponseModel> call = apiService.rideSearch("ride/search/amount/" + autoCompleteLocation.getText().toString().replaceAll(" ", "+") + "/" + autoCompleteDestination.getText().toString().replaceAll(" ", "+") + "/" + String.valueOf(seatNumber) + "/" + poolRiding);
                call.enqueue(new retrofit2.Callback<RideSearchResponseModel>() {
                    @Override
                    public void onResponse(Call<RideSearchResponseModel> call, Response<RideSearchResponseModel> response) {
                        Log.d("response", "onResponse: "+response.raw());
                        Log.d("response", "onResponse: "+response.body().getMessage());
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
                        toLat + "", toLong + "", poolRiding, String.valueOf(seatNumber), appPrefes.getData(Constants.USER_UNIVERSITY_ID), tvAmount.getText().toString(), gpsTracker.getLatitude() + "", gpsTracker.getLongitude() + "");
                Log.d("params", "callBookRideApi: "+ appPrefes.getData(Constants.USER_ID) +","+ autoCompleteLocation.getText().toString().replaceAll(" ", "+")+","+
                        fromLat +","+ fromLong +","+ autoCompleteDestination.getText().toString().replaceAll(" ", "+")+","+
                        toLat+","+ toLong +","+ poolRiding+","+ String.valueOf(seatNumber)+","+ appPrefes.getData(Constants.USER_UNIVERSITY_ID)+","+ tvAmount.getText().toString()+","+ gpsTracker.getLatitude()  +","+ gpsTracker.getLongitude());

                call.enqueue(new retrofit2.Callback<BookRideResponseModel>() {
                    @Override
                    public void onResponse(Call<BookRideResponseModel> call, Response<BookRideResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        Log.e("response", "onResponse: "+ response.raw() );
                        Log.e("response", "onResponse: "+ response.body().isStatus() );
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
//                                cardViewNext.setVisibility(View.VISIBLE);
//                                cbPool.setVisibility(View.VISIBLE);
                                location.setVisibility(View.VISIBLE);
                                cardViewRideDetails.setVisibility(View.GONE);
//                                cardViewMove.setVisibility(View.GONE);
//                                tvBookFutureRide.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(BOOK_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookRideResponseModel> call, Throwable t) {
                        Log.e("response", "onResponse: "+ t.getLocalizedMessage() );
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
                        toLat + "", toLong + "", poolRiding, String.valueOf(seatNumber), selectedCollegeId, tvAmount.getText().toString(), gpsTracker.getLatitude() + "", gpsTracker.getLongitude() + ""
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
                                locations.setVisibility(View.VISIBLE);
                                Toast.makeText(HomeActivity.this, "Ride booked!", Toast.LENGTH_SHORT).show();
                            } else {
                                showRequestSuccessDialog("Oops!", response.body().getMessage(), "Okay", SEARCH_FAILED_DAILOG);
                                currentStep = 1;
                                locations.setVisibility(View.VISIBLE);
                                cardViewNext.setVisibility(View.VISIBLE);
                                cbPool.setVisibility(View.VISIBLE);
                                cardViewRideDetails.setVisibility(View.GONE);
                                cardViewMove.setVisibility(View.GONE);
                                tvBookFutureRide.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            locations.setVisibility(View.VISIBLE);
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
                Log.d("response", "rides/cancel/" + currentRideId + "/" + freeOrPaid);
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
                        Log.d("current_ride", "onResponse: "+appPrefes.getData(Constants.USER_ID));
                        Log.d("current_ride", "onResponse: "+response.raw());
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().getStatus()) {
                                boolean isLiveRide = false;
//                                Log.d("current_ride_exc", "length "+response.body().getData().size());
                                for (int i = 0; i < response.body().getData().size(); i++) {
//                                    Log.d("current_ride_exc", "each i "+i);
                                    if (response.body().getData().get(i).getRideType().equals("live")) {
                                        setRiderDetails(response.body().getData().get(i));
                                        currentRideId = response.body().getData().get(i).getRideId() + "";
                                        cardViewMove.setVisibility(View.GONE);
                                        tvBookFutureRide.setVisibility(View.GONE);
                                        cardViewRideDetails.setVisibility(View.GONE);
                                        cardViewNext.setVisibility(View.GONE);
                                        cbPool.setVisibility(View.GONE);
//                                        Log.d("current_ride_exc", "each i "+i);
                                        toLat = Double.parseDouble(response.body().getData().get(i).getPolyLines().getEnd().getLat().toString().substring(0, response.body().getData().get(i).getPolyLines().getEnd().getLat().toString().length()-1));
                                        toLong = Double.parseDouble(response.body().getData().get(i).getPolyLines().getEnd().getLng().toString().substring(0, response.body().getData().get(i).getPolyLines().getEnd().getLng().toString().length()-1));
                                    }
                                    if (response.body().getData().get(i).getRideType().equals("live")) {
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
                                        Log.d("current_ride_exc1", "onResponse: "+e.getLocalizedMessage());
                                        e.printStackTrace();
                                    }
                                    cbPool.setChecked(true);
                                }
                            } else {
                            }
                        } catch (Exception e) {
                            Log.d("current_ride_exc2", "onResponse: "+e.getLocalizedMessage());
                            e.printStackTrace();
                            showServerErrorAlert(VIEW_CURRENT_RIDE_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewCurrentRideResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
//                        Log.d("failed", "onResponse: "+appPrefes.getData(Constants.USER_ID));
                        Log.d("failed", "onResponse: "+t.getLocalizedMessage());
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VIEW_CURRENT_RIDE_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("current_ride_fail_exc", "onResponse: "+e.getLocalizedMessage());
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
