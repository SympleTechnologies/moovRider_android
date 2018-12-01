package com.moovapp.riderapp.preLogin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.preLogin.signUp.SignUpActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTBaseActivity;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.SocialLoginResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LandingPageActivity extends LMTBaseActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private final int SOCIAL_LOGIN_API = 4;

    //FB
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton loginButton;
    private ProfileTracker profileTracker;

    //GOOGLE
    private GoogleApiClient mGoogleApiClient;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    private GoogleSignInAccount acctMain;

    @BindView(R.id.background_image)
    ImageView backgroundImage;
    @BindView(R.id.sign_in_btn)
    Button signIn;
    @BindView(R.id.sign_up_button)
    Button signUp;
    @BindView(R.id.google)
    ImageView google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_landing_page);
        fbInIt();
        initGooglePlus();
        getDeviceToken();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.moovapp.riderapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        ButterKnife.bind(this);

        backgroundImage.setAlpha(-40);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.putExtra("RegistrationType", "Normal");
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void getDeviceToken() {
        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
        String token = FirebaseInstanceId.getInstance().getToken();
        appPrefes.SaveData(Constants.DEVICE_TOKEN, token);
    }

    //Social Login
    //GooglePlus Login - start

    public void googlePlusLogin(View view) {
        signIn();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            String image = "";
            try {
                image = account.getPhotoUrl().toString();
            } catch (Exception e) {
                e.printStackTrace();
                image = "";
            }
            callSocialLoginApi("google", account.getId(), account.getEmail(), account.getDisplayName(), image);
            try {
                mGoogleSignInClient.signOut();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void initGooglePlus() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.i(LoginPage.class.getSimpleName(), "Can't connect to Google Play Services!");
        Toast.makeText(this, "Network Error:Please check network connection and try again", Toast.LENGTH_SHORT).show();
    }
    //GooglePlus Login - end

    //Fb login - start
    /*Facebook Login  calback*/
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity Response ", response.toString() + " " + object);
                            try {
                                if (object.getString("email") == null) {
                                    Toast.makeText(LandingPageActivity.this, "Email not available!", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("Social Login: " + "facebook " + object.getString("id") + " " + object.getString("name") + "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large" + object.getString("email"));
                                    myProgressDialog.setProgress(false);
                                    callSocialLoginApi("facebook", object.getString("id"), object.getString("email"), object.getString("name"), "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LandingPageActivity.this, "Email not available!", Toast.LENGTH_SHORT).show();
                            }
                            LoginManager.getInstance().logOut();
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,link,picture");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        }
    };

    public void fbInIt() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, callback);
    }

    public void fbLogin(View view) {
        loginButton.callOnClick();
    }

    //Fb login - end

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void callSocialLoginApi(final String loginType, final String id, final String email, final String name, final String profilePic) {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<SocialLoginResponseModel> call = apiService.socialLogin(loginType, id, "android", appPrefes.getData(Constants.DEVICE_TOKEN), "2.0");
                call.enqueue(new retrofit2.Callback<SocialLoginResponseModel>() {
                    @Override
                    public void onResponse(Call<SocialLoginResponseModel> call, Response<SocialLoginResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Intent intent = new Intent(LandingPageActivity.this, SignUpActivity.class);
                                intent.putExtra("RegistrationType", "Social");
                                intent.putExtra("loginType", loginType);
                                intent.putExtra("id", id);
                                intent.putExtra("email", email);
                                intent.putExtra("name", name);
                                intent.putExtra("profilePic", profilePic);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                appPrefes.SaveData(Constants.USER_ID, response.body().getData().getUser_details().getU_id() + "");
                                appPrefes.SaveData(Constants.USER_FIRST_NAME, response.body().getData().getUser_details().getU_first_name() + "");
                                appPrefes.SaveData(Constants.ACCESS_TOKEN, response.body().getData().getAccess_token());
                                appPrefes.SaveData(Constants.USER_PROFILE_PIC, response.body().getData().getUser_pic_url());
                                appPrefes.SaveDataBoolean(Constants.USER_LOGGED_IN_STATUS, true);
                                Intent intent = new Intent(LandingPageActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(SOCIAL_LOGIN_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialLoginResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(SOCIAL_LOGIN_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(SOCIAL_LOGIN_API);
            }
        } else {
            showNoInternetAlert(SOCIAL_LOGIN_API);
        }
    }
}
