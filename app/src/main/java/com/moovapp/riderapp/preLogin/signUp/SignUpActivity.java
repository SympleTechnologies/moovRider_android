package com.moovapp.riderapp.preLogin.signUp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTBaseActivity;
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
 * Created by Lijo Mathew Theckanal on 18-Jul-18.
 */

public class SignUpActivity extends LMTBaseActivity implements Validator.ValidationListener {

    private final int LIST_COLLEGES_API = 1;
    private final int LIST_USERS_API = 2;
    private final int REGISTER_API = 3;
    private final int CHECK_EMAIL_API = 4;
    private final int EXIT_DIALOG = 5;
    private final int REGISTRATION_SUCCESS_DIALOG = 6;

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

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.sign_up_activity);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        registrationType = getIntent().getStringExtra("RegistrationType");
        if (registrationType.equals("Social")) {
            setSocialRegistration();
            authModeStr = "social";
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
        socialLoginType = registrationType = getIntent().getStringExtra("loginType");
        socialLoginId = registrationType = getIntent().getStringExtra("id");
        socialEmail = registrationType = getIntent().getStringExtra("email");
        socialName = registrationType = getIntent().getStringExtra("name");
        profilePic = registrationType = getIntent().getStringExtra("profilePic");
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.tvSignIn)
    public void tvSignInClick() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (currentPage == 1) {
            showAlertDialog("Exit", "Are you sure you want to exit from registration?", "Yes", "Cancel", EXIT_DIALOG);
        } else if (currentPage == 2) {
            llAlreadyHaveAccount.setVisibility(View.VISIBLE);
            layoutOne.setVisibility(View.VISIBLE);
            layoutTwo.setVisibility(View.GONE);
            imgSeekBar.setImageResource(R.mipmap.slide_bar_one);
            currentPage = 1;
        } else if (currentPage == 3) {
            llAlreadyHaveAccount.setVisibility(View.INVISIBLE);
            layoutThree.setVisibility(View.GONE);
            layoutTwo.setVisibility(View.VISIBLE);
            imgSeekBar.setImageResource(R.mipmap.slide_bar_two);
            currentPage = 2;
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
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
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
                finish();
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
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
        WhiteSpinnerAdapter classAdapter = new WhiteSpinnerAdapter(this, R.layout.white_spinner_list_item, R.id.title, collegeList);
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
        WhiteSpinnerAdapter classAdapter = new WhiteSpinnerAdapter(this, R.layout.white_spinner_list_item, R.id.title, userList);
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

                RequestBody f_name = RequestBody.create(MediaType.parse("text/plain"), edFirstName.getText().toString());
                map.put("f_name", f_name);
                RequestBody l_name = RequestBody.create(MediaType.parse("text/plain"), edLastName.getText().toString());
                map.put("l_name", l_name);
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edEmail.getText().toString());
                map.put("email", email);
                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), edPassword.getText().toString());
                map.put("password", password);

                RequestBody college = RequestBody.create(MediaType.parse("text/plain"), selectedCollegeId);
                map.put("college", college);
                RequestBody user_type = RequestBody.create(MediaType.parse("text/plain"), selectedUserId);
                map.put("user_type", user_type);

                String cCode = codePicker.getSelectedCountryCode();
                if (!cCode.contains("+")) {
                    cCode = "+" + cCode;
                }
                RequestBody phone_country = RequestBody.create(MediaType.parse("text/plain"), cCode);
                map.put("phone_country", phone_country);
                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), edPhoneNumber.getText().toString());
                map.put("phone", phone);

                RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), "");
                map.put("gender", gender);
                RequestBody auth_mode = RequestBody.create(MediaType.parse("text/plain"), authModeStr);
                map.put("auth_mode", auth_mode);
                RequestBody auth_provider = RequestBody.create(MediaType.parse("text/plain"), socialLoginType);
                map.put("auth_provider", auth_provider);
                RequestBody auth_uid = RequestBody.create(MediaType.parse("text/plain"), socialLoginId);
                map.put("auth_uid", auth_uid);

                RequestBody device_type = RequestBody.create(MediaType.parse("text/plain"), "android");
                map.put("device_type", device_type);
                RequestBody device_id = RequestBody.create(MediaType.parse("text/plain"), appPrefes.getData(Constants.DEVICE_TOKEN));
                map.put("device_id", device_id);
                RequestBody push_token = RequestBody.create(MediaType.parse("text/plain"), appPrefes.getData(Constants.DEVICE_TOKEN));
                map.put("push_token", push_token);
                RequestBody app_version = RequestBody.create(MediaType.parse("text/plain"), "2.0");
                map.put("app_version", app_version);

                if (registrationType.equals("Social")) {
                    RequestBody image = RequestBody.create(MediaType.parse("text/plain"), profilePic);
                    map.put("image", image);
                }

                Call<RegistartionResponseModel> call = apiService.register(map);
                call.enqueue(new Callback<RegistartionResponseModel>() {
                    @Override
                    public void onResponse(Call<RegistartionResponseModel> call, Response<RegistartionResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.body().isStatus()) {
                                Toast.makeText(getBaseContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                finish();
                break;
        }
    }

    @Override
    public void onSuccessDialogClick(int apiCode) {
        super.onClickAlertOkButton(apiCode);
        switch (apiCode) {
            case REGISTRATION_SUCCESS_DIALOG:
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }
}
