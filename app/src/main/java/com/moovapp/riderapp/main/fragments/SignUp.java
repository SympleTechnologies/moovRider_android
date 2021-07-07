package com.moovapp.riderapp.main.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.preLogin.SignInSignUp;
import com.moovapp.riderapp.preLogin.signUp.SignUpActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTFragment;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.CheckEmailResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.RegistartionResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.SelectCollegeResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.SelectUserTypeResponseModel;
import com.moovapp.riderapp.utils.spinnerAdapter.WhiteSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Manuel Chris-Ogar on 1/23/2019.
 */
public class SignUp extends LMTFragment implements Validator.ValidationListener {

    private final int LIST_COLLEGES_API = 1;
    private final int LIST_USERS_API = 2;
    private final int REGISTER_API = 3;
    private final int CHECK_EMAIL_API = 4;
    private final int EXIT_DIALOG = 5;
    private final int REGISTRATION_SUCCESS_DIALOG = 6;
    public SignInSignUp signInSignUp;

    @BindView(R.id.imgSeekBar)
    ImageView imgSeekBar;
    @BindView(R.id.tvNext)
    TextView tvNext;
    @BindView(R.id.llAlreadyHaveAccount)
    LinearLayout llAlreadyHaveAccount;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.layoutOne)
    View layoutOne;
    @BindView(R.id.layoutTwo)
    View layoutTwo;
    @BindView(R.id.layoutThree)
    View layoutThree;
    @NotEmpty(trim = true)
    @BindView(R.id.edFirstName)
    EditText edFirstName;
    @NotEmpty(trim = true)
    @BindView(R.id.edLastName)
    EditText edLastName;
    @Email
    @BindView(R.id.edEmail)
    EditText edEmail;
    @Password(min = 4, message = "password must be at least 4 characters")
    @BindView(R.id.edPassword)
    EditText edPassword;
    @NotEmpty(trim = true)
    @ConfirmPassword
    @BindView(R.id.edConfirmPassword)
    EditText edConfirmPassword;
    @BindView(R.id.spinnerInstitution)
    Spinner spinnerInstitution;
    @BindView(R.id.spinnerRole)
    Spinner spinnerRole;
    @BindView(R.id.codePicker)
    CountryCodePicker codePicker;
    @BindView(R.id.edPhoneNumber)
    EditText edPhoneNumber;

    private Validator validator;

    private int currentPage = 1;
    private SelectCollegeResponseModel.DataEntity selectCollegeDataEntity;
    private SelectUserTypeResponseModel.DataEntity selectUserTypeDataEntity;
    private List<String> collegeList;
    private List<String> collegeIdList;
    private List<String> userList;
    private List<String> userIdList;
    private String selectedCollegeId = "";
    private String selectedUserId = "";
    private String authModeStr = "email";
    private String registrationType = "Normal";
    private String socialLoginType = "";
    private String socialLoginId = "";
    private String socialEmail = "";
    private String socialName = "";
    private String profilePic = "";
    private boolean isEmailOk = true;
    private boolean isNextClick = false;


    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onCreate(Bundle arg0) {
        signInSignUp = (SignInSignUp) getActivity();
        super.onCreate(arg0);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        edEmail = view.findViewById(R.id.edEmail);
        edFirstName = view.findViewById(R.id.edFirstName);
        rootView = view.findViewById(R.id.rootView);
        llAlreadyHaveAccount = view.findViewById(R.id.llAlreadyHaveAccount);
        layoutOne = view.findViewById(R.id.layoutOne);
        layoutTwo = view.findViewById(R.id.layoutTwo);
        layoutThree = view.findViewById(R.id.layoutThree);
        imgSeekBar = view.findViewById(R.id.imgSeekBar);
        spinnerInstitution = view.findViewById(R.id.spinnerInstitution);
        spinnerRole = view.findViewById(R.id.spinnerRole);
//        tvNext  =view.findViewById(R.id.tvNext);
        validator = new Validator(this);
        validator.setValidationListener(this);
        if (getArguments() != null){
            registrationType = getArguments().getString("RegistrationType");
            if (registrationType.equals("Social")) {
                setSocialRegistration();
                authModeStr = "social";
            }
        }



        callListCollegesApi();
        keyboardListener();
        setEmailListener();
    }

    private void setEmailListener() {
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    isNextClick = false;
                    callCheckEmailApi();
                }
            }
        });
        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEmailOk = false;
            }
        });
    }

    private void setSocialRegistration() {
        socialLoginType = registrationType = getArguments().getString("loginType");
        socialLoginId = registrationType = getArguments().getString("id");
        socialEmail = registrationType = getArguments().getString("email");
        socialName = registrationType = getArguments().getString("name");
        profilePic = registrationType = getArguments().getString("profilePic");
        edFirstName.setText(socialName);
        edEmail.setText(socialEmail);
        changePage();
    }

    private void keyboardListener() {
       rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                Log.d("HAHAHA", "keypadHeight = " + keypadHeight);
                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    if (currentPage == 1) {
                        tvNext.setVisibility(View.GONE);
                        llAlreadyHaveAccount.setVisibility(View.GONE);
                    }
                } else {
                    // keyboard is closed
                    if (currentPage == 1) {
                        tvNext.setVisibility(View.VISIBLE);
                        llAlreadyHaveAccount.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }



    @OnClick(R.id.tvSignIn)
    public void tvSignInClick() {
        getActivity().finish();
    }

    @Override
    public boolean onBackPressed() {
        if (currentPage == 1) {
            showAlertDialog("Exit", "Are you sure you want to exit from registration?", "Yes", "Cancel", EXIT_DIALOG);
            return true;
        } else if (currentPage == 2) {
            llAlreadyHaveAccount.setVisibility(View.VISIBLE);
            layoutOne.setVisibility(View.VISIBLE);
            layoutTwo.setVisibility(View.GONE);
            imgSeekBar.setImageResource(R.mipmap.slide_bar_one);
            currentPage = 1;
            return true;
        } else if (currentPage == 3) {
            llAlreadyHaveAccount.setVisibility(View.INVISIBLE);
            layoutThree.setVisibility(View.GONE);
            layoutTwo.setVisibility(View.VISIBLE);
            imgSeekBar.setImageResource(R.mipmap.slide_bar_two);
            currentPage = 2;
            return true;
        }else{
            return false;
        }
    }

    @OnClick(R.id.tvNext)
    public void tvNextClick() {
        if (currentPage == 1) {
            if (isEmailOk) {
                validator.validate();
            } else {
                isNextClick = true;
                callCheckEmailApi();
            }
        } else if (currentPage == 2) {
            if (!selectedCollegeId.equals("")) {
                if (!selectedUserId.equals("")) {
                    changePage();
                } else {
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
        } else if (currentPage == 3) {
            if (edPhoneNumber.getText().toString().trim().length() < 8) {
                edPhoneNumber.setError("Please enter a valid mobile number");
            } else {
                callRegisterApi();
            }
        }
    }

    private void changePage() {
        switch (currentPage) {
            case 1:
                llAlreadyHaveAccount.setVisibility(View.INVISIBLE);
                layoutOne.setVisibility(View.GONE);
                layoutTwo.setVisibility(View.VISIBLE);
                imgSeekBar.setImageResource(R.mipmap.slide_bar_two);
                currentPage = 2;
                break;
            case 2:
                layoutTwo.setVisibility(View.GONE);
                layoutThree.setVisibility(View.VISIBLE);
                tvNext.setText("SIGN UP");
                imgSeekBar.setImageResource(R.mipmap.slide_bar_three);
                currentPage = 3;
                break;
            case 3:
                mainActivity.finish();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        changePage();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setCollegeSpinner() {
        collegeList = new ArrayList<>();
        collegeIdList = new ArrayList<>();
        for (int i = 0; i < selectCollegeDataEntity.getDetails().size(); i++) {
            collegeList.add(selectCollegeDataEntity.getDetails().get(i).getName());
            collegeIdList.add(selectCollegeDataEntity.getDetails().get(i).getId() + "");
        }
        WhiteSpinnerAdapter classAdapter = new WhiteSpinnerAdapter(getActivity(), R.layout.white_spinner_list_item, R.id.title, collegeList);
        spinnerInstitution.setAdapter(classAdapter);
        spinnerInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCollegeId = collegeIdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUserTypeSpinner() {
        userList = new ArrayList<>();
        userIdList = new ArrayList<>();
        for (int i = 0; i < selectUserTypeDataEntity.getDetails().size(); i++) {
            userList.add(selectUserTypeDataEntity.getDetails().get(i).getName());
            userIdList.add(selectUserTypeDataEntity.getDetails().get(i).getId() + "");
        }
        WhiteSpinnerAdapter classAdapter = new WhiteSpinnerAdapter(getActivity(), R.layout.white_spinner_list_item, R.id.title, userList);
        spinnerRole.setAdapter(classAdapter);
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUserId = userIdList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void callRegisterApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Map<String, RequestBody> map = new HashMap<>();
                Log.d("maps", "callRegisterApi: "+ edFirstName.getText().toString());
                RequestBody f_name = RequestBody.create(MediaType.parse("text/plain"), edFirstName.getText().toString());
                map.put("f_name", f_name);
                RequestBody l_name = RequestBody.create(MediaType.parse("text/plain"), edLastName.getText().toString());
                map.put("l_name", l_name);
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edEmail.getText().toString());
                map.put("email", email);
                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), edPassword.getText().toString());
                map.put("password", password);
                Log.d("maps", "callRegisterApi: "+ edPassword.getText().toString());
                RequestBody college = RequestBody.create(MediaType.parse("text/plain"), selectedCollegeId);
                Log.d("maps", "callRegisterApi: "+ selectedCollegeId);
                map.put("college", college);
                RequestBody user_type = RequestBody.create(MediaType.parse("text/plain"), selectedUserId);
                Log.d("maps", "callRegisterApi: "+ selectedUserId);
                map.put("user_type", user_type);
                Log.d("maps", "callRegisterApi: "+ user_type);

                String cCode = codePicker.getSelectedCountryCode();
                if (!cCode.contains("+")) {
                    cCode = "+" + cCode;
                }
                RequestBody phone_country = RequestBody.create(MediaType.parse("text/plain"), cCode);
                Log.d("maps", "cCode: "+ cCode);
                map.put("phone_country", phone_country);
                Log.d("maps", "phone_country: "+ phone_country);
                Log.d("maps", "phone: "+ edPhoneNumber.getText().toString());
                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), edPhoneNumber.getText().toString());
                map.put("phone", phone);

                RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), "");
                map.put("gender", gender);
                RequestBody auth_mode = RequestBody.create(MediaType.parse("text/plain"), authModeStr);
                Log.d("maps", "authModeStr: "+ authModeStr);
                map.put("auth_mode", auth_mode);
                RequestBody auth_provider = RequestBody.create(MediaType.parse("text/plain"), socialLoginType);
                map.put("auth_provider", auth_provider);
                Log.d("maps", "callRegisterApi: "+ socialLoginType);
                RequestBody auth_uid = RequestBody.create(MediaType.parse("text/plain"), socialLoginId);
                Log.d("maps", "callRegisterApi: "+ socialLoginId);
                map.put("auth_uid", auth_uid);
                Log.d("maps", "callRegisterApi: "+ auth_uid);
                RequestBody device_type = RequestBody.create(MediaType.parse("text/plain"), "android");
                map.put("device_type", device_type);
                RequestBody device_id = RequestBody.create(MediaType.parse("text/plain"), appPrefes.getData(Constants.DEVICE_TOKEN));
                Log.d("maps", "device_id: "+ appPrefes.getData(Constants.DEVICE_TOKEN));
                Log.d("maps", "auth_uid: "+ auth_uid);
                map.put("device", device_id);
                RequestBody push_token = RequestBody.create(MediaType.parse("text/plain"), appPrefes.getData(Constants.DEVICE_TOKEN));
                map.put("push_token", push_token);
                Log.d("maps", "auth_uid: "+ auth_uid);
                Log.d("maps", "appPrefes.getData(Constants.DEVICE_TOKEN "+ appPrefes.getData(Constants.DEVICE_TOKEN));
                RequestBody app_version = RequestBody.create(MediaType.parse("text/plain"), "2.0");
                map.put("app_version", app_version);



                if (registrationType.equals("Social")) {
                    RequestBody image = RequestBody.create(MediaType.parse("text/plain"), profilePic);
                    map.put("image", image);
                    Log.d("maps", "auth_uid: "+ profilePic);
                }

                Gson gson = new Gson();
                String json = gson.toJson(map);


                Log.d("Json", "callRegisterApi: "+json.toString());

                Call<RegistartionResponseModel> call = apiService.register(map);
                call.enqueue(new Callback<RegistartionResponseModel>() {
                    @Override
                    public void onResponse(Call<RegistartionResponseModel> call, Response<RegistartionResponseModel> response) {
                        myProgressDialog.dismissProgress();
//                        Log.e("response", "onResponse: "+response.body().isStatus() );
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                appPrefes.SaveData(Constants.USER_ID, response.body().getData().getUser_details().getU_id() + "");
                                appPrefes.SaveData(Constants.USER_FIRST_NAME, response.body().getData().getUser_details().getU_first_name() + "");
                                appPrefes.SaveData(Constants.ACCESS_TOKEN, response.body().getData().getAccess_token());
                                appPrefes.SaveData(Constants.USER_PROFILE_PIC, response.body().getData().getUser_pic_url());
                                appPrefes.SaveDataBoolean(Constants.USER_LOGGED_IN_STATUS, true);
                                showRequestSuccessDialog("Success", "You are successfully registered! Please login to access your account", "Okay", REGISTRATION_SUCCESS_DIALOG);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(REGISTER_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegistartionResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(REGISTER_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(REGISTER_API);
            }
        } else {
            showNoInternetAlert(REGISTER_API);
        }
    }

    private void callListCollegesApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<SelectCollegeResponseModel> call = apiService.selectCollege("");
                call.enqueue(new retrofit2.Callback<SelectCollegeResponseModel>() {
                    @Override
                    public void onResponse(Call<SelectCollegeResponseModel> call, Response<SelectCollegeResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                selectCollegeDataEntity = response.body().getData();
                                setCollegeSpinner();
                                callListUserTypesApi();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(LIST_COLLEGES_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<SelectCollegeResponseModel> call, Throwable t) {
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

    private void callListUserTypesApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<SelectUserTypeResponseModel> call = apiService.selectUserType("");
                call.enqueue(new retrofit2.Callback<SelectUserTypeResponseModel>() {
                    @Override
                    public void onResponse(Call<SelectUserTypeResponseModel> call, Response<SelectUserTypeResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                // TODO: 1/9/2019 better error catching
                                if (response.body().getMessage().contains("Email")) {
                                    llAlreadyHaveAccount.setVisibility(View.VISIBLE);
                                    layoutOne.setVisibility(View.VISIBLE);
                                    layoutTwo.setVisibility(View.GONE);
                                    imgSeekBar.setImageResource(R.mipmap.slide_bar_one);
                                    currentPage = 1;
                                    edEmail.setError(response.body().getMessage());
                                }
//                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                selectUserTypeDataEntity = response.body().getData();
                                setUserTypeSpinner();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(LIST_COLLEGES_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<SelectUserTypeResponseModel> call, Throwable t) {
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

    private void callCheckEmailApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<CheckEmailResponseModel> call = apiService.checkEmail(edEmail.getText().toString());
                call.enqueue(new retrofit2.Callback<CheckEmailResponseModel>() {
                    @Override
                    public void onResponse(Call<CheckEmailResponseModel> call, Response<CheckEmailResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                isEmailOk = true;
                                if (isNextClick) {
                                    validator.validate();
                                }
                            } else {
                                edEmail.setError("Email already exist!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(LIST_COLLEGES_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckEmailResponseModel> call, Throwable t) {
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

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case LIST_COLLEGES_API:
                callListCollegesApi();
                break;
            case LIST_USERS_API:
                callListUserTypesApi();
                break;
            case REGISTER_API:
                callRegisterApi();
                break;
            case CHECK_EMAIL_API:
                callCheckEmailApi();
                break;
        }
    }

    @Override
    public void onClickAlertOkButton(int apiCode) {
        super.onClickAlertOkButton(apiCode);
        switch (apiCode) {
            case EXIT_DIALOG:
               getActivity().finish();
                break;
        }
    }

    @Override
    public void onSuccessDialogClick(int apiCode) {
        super.onClickAlertOkButton(apiCode);
        switch (apiCode) {
            case REGISTRATION_SUCCESS_DIALOG:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
