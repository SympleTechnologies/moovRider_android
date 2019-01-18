package com.moovapp.riderapp.main.wallet.payment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.wallet.WalletActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTBaseActivity;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.InitPaymentResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.VerifyPaymentResponseModel;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Lijo Mathew Theckanal on 20-Jul-18.
 */

public class PaymentActivity extends LMTBaseActivity {

    private final int INIT_PAYMENT_API = 1;
    private final int VERIFY_PAYMENT_API = 2;
    private final int PAYMENT_SUCCSSS_DIALOG = 3;
    private final int PAYMENT_ALERT_DIALOG = 4;

    @BindView(R.id.edAmount)
    TextView edAmount;
    @BindView(R.id.edCardNumber)
    EditText edCardNumber;
    @BindView(R.id.edDate)
    EditText edDate;
    @BindView(R.id.edCvv)
    EditText edCvv;
    @BindView(R.id.edName)
    EditText edName;

    private boolean isAllFieldsAreOkay = true;
    private String accessCode = "";
    private String reference = "";
    private String paymentReference = "";
    private Card card;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.payment_activity);
        ButterKnife.bind(this);
        PaystackSdk.initialize(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.navBackButton)
    public void navBackButtonClick() {
        finish();
    }

    @OnClick(R.id.edDate)
    public void edDateClick() {
        showDatePickerDialog();
    }

    @OnClick(R.id.tvPayNow)
    public void tvPayNowClick() {
        isAllFieldsAreOkay = true;
        if (edAmount.getText().toString().trim().length() < 1) {
            edAmount.setError("This field is required!");
            isAllFieldsAreOkay = false;
        }
        if (edCardNumber.getText().toString().trim().length() < 1) {
            edCardNumber.setError("This field is required!");
            isAllFieldsAreOkay = false;
        }
        if (edDate.getText().toString().trim().length() < 1) {
            edDate.setError("This field is required!");
            isAllFieldsAreOkay = false;
        }
        if (edCvv.getText().toString().trim().length() < 1) {
            edCvv.setError("This field is required!");
            isAllFieldsAreOkay = false;
        }
        if (edName.getText().toString().trim().length() < 1) {
            edName.setError("This field is required!");
            isAllFieldsAreOkay = false;
        }

        if (isAllFieldsAreOkay) {
            String[] date = edDate.getText().toString().split("/");
            card = new Card(edCardNumber.getText().toString(), Integer.parseInt(date[0]), Integer.parseInt(date[1]), edCvv.getText().toString(), edName.getText().toString());
            if (card.isValid()) {
                showRequestSuccessDialog("Alert", "Bank charges applies", "Okay", PAYMENT_ALERT_DIALOG);
            } else {
                Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void performCharge() {
        //create a Charge object
        Charge charge = new Charge();
        //set the card to charge
        charge.setCard(card);
        charge.setAccessCode(accessCode);
        charge.setAmount(Integer.parseInt(edAmount.getText().toString())); //test amount
        PaystackSdk.chargeCard(PaymentActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.
                myProgressDialog.dismissProgress();
                paymentReference = transaction.getReference();
                Toast.makeText(PaymentActivity.this, "Transaction Successful! payment reference: " + paymentReference, Toast.LENGTH_LONG).show();
                callVerifyPaymentApi();
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                myProgressDialog.dismissProgress();
                Toast.makeText(PaymentActivity.this, "Error" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(PaymentActivity.this, "Error" + transaction.getReference(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePickerDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_date_picker);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        final TextView tvSelect = (TextView) dialog.findViewById(R.id.tvSelect);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);

        Calendar cal = Calendar.getInstance();
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year);
        yearPicker.setMaxValue(year + 15);
        yearPicker.setValue(year);

        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthPicker.getValue() < 10) {
                    edDate.setText("0" + monthPicker.getValue() + "/" + yearPicker.getValue());
                } else {
                    edDate.setText(monthPicker.getValue() + "/" + yearPicker.getValue());
                }
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void callInitPaymentApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<InitPaymentResponseModel> call = apiService.initPayment(edAmount.getText().toString(), appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<InitPaymentResponseModel>() {
                    @Override
                    public void onResponse(Call<InitPaymentResponseModel> call, Response<InitPaymentResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                accessCode = response.body().getData().getAccess_code();
                                reference = response.body().getData().getReference();
                                myProgressDialog.setProgress(false);

                                performCharge();
                            } else {
                                Toast.makeText(PaymentActivity.this, "Error initiating payment gateway", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(INIT_PAYMENT_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<InitPaymentResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        Toast.makeText(PaymentActivity.this, "not working", Toast.LENGTH_SHORT).show();
                        System.out.println("t.toString : " + t.getLocalizedMessage());
                        showServerErrorAlert(INIT_PAYMENT_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(INIT_PAYMENT_API);
            }
        } else {
            showNoInternetAlert(INIT_PAYMENT_API);
        }
    }

    private void callVerifyPaymentApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<VerifyPaymentResponseModel> call = apiService.verifyPayment(paymentReference, appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<VerifyPaymentResponseModel>() {
                    @Override
                    public void onResponse(Call<VerifyPaymentResponseModel> call, Response<VerifyPaymentResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                myProgressDialog.setProgress(false);
                                showRequestSuccessDialog("Success", "You have successfully loaded your wallet!", "Okay", PAYMENT_SUCCSSS_DIALOG);
                            } else {
                                Toast.makeText(PaymentActivity.this, "Error initiating payment gateway", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(VERIFY_PAYMENT_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyPaymentResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(VERIFY_PAYMENT_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(VERIFY_PAYMENT_API);
            }
        } else {
            showNoInternetAlert(VERIFY_PAYMENT_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case INIT_PAYMENT_API:
                callInitPaymentApi();
                break;
            case VERIFY_PAYMENT_API:
                callVerifyPaymentApi();
                break;
        }
    }

    @Override
    public void onSuccessDialogClick(int apiCode) {
        super.onSuccessDialogClick(apiCode);
        switch (apiCode) {
            case PAYMENT_SUCCSSS_DIALOG:
                WalletActivity.isWalletReacharged = true;
                finish();
                break;
            case PAYMENT_ALERT_DIALOG:
                callInitPaymentApi();
                break;
        }
    }

}
