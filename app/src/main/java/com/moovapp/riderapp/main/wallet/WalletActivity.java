package com.moovapp.riderapp.main.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.wallet.payment.PaymentActivity;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.LMTBaseActivity;
import com.moovapp.riderapp.utils.retrofit.ApiClient;
import com.moovapp.riderapp.utils.retrofit.ApiInterface;
import com.moovapp.riderapp.utils.retrofit.responseModels.CheckPhoneNumberResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ListBanksResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.TransferAmountToBankResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.TransferAmountToWalletResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewWalletBalanceResponseModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Lijo Mathew Theckanal on 19-Jul-18.
 */

public class WalletActivity extends LMTBaseActivity {

    private final int VIEW_WALLET_BALANCE_API = 1;
    private final int TRANSFER_WALLET_BALANCE_TO_WALLET_API = 2;
    private final int LIST_BANKS_API = 3;
    private final int TRANSFER_WALLET_BALANCE_TO_BANK_API = 4;
    private final int CHECK_PHONE_NUMBER_API = 5;

    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.llTransfer1)
    LinearLayout llTransfer1;
    @BindView(R.id.llTransfer2)
    LinearLayout llTransfer2;
    @BindView(R.id.llTransfer3)
    LinearLayout llTransfer3;
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.spinnerBank)
    Spinner spinnerBank;
    @BindView(R.id.edAccountNumber)
    EditText edAccountNumber;
    @BindView(R.id.edBankAmount)
    EditText edBankAmount;

    @BindView(R.id.edPhoneNumber)
    EditText edPhoneNumber;
    @BindView(R.id.edWalletAmount)
    EditText edWalletAmount;
    @BindView(R.id.edNameUser)
    EditText edNameUser;

    public static boolean isWalletReacharged = false;
    private String[] bankNames;
    private String[] bankCodes;
    private String selectedBankCode = "";
    private String userToSendMoney = "";
    SwipeRefreshLayout refreshWallet;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.wallet_layout);
        ButterKnife.bind(this);
        refreshWallet = findViewById(R.id.refreshWallet);
        refreshWallet.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callViewWalletBalanceApi();
            }
        });
        callViewWalletBalanceApi();
        callListBanksApi();
        setPhoneNumberListener();

    }

    private void setPhoneNumberListener() {
        edPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPhoneNumber.getText().toString().length() == 10) {
                    callCheckPhoneNumberApi();
                } else {
                    edNameUser.setText("");
                }
            }
        });
    }

    @OnClick(R.id.tvWallet)
    public void tvWalletClick() {
        llTransfer1.setVisibility(View.GONE);
        llTransfer2.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvBank)
    public void tvBankClick() {
        llTransfer1.setVisibility(View.GONE);
        llTransfer3.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvTransferWallet)
    public void tvTransferWalletClick() {
        try {
            if (edPhoneNumber.getText().toString().length() != 10) {
                edPhoneNumber.setError("Enter a valid number");
            } else if (edNameUser.getText().toString().length()<1) {
                edPhoneNumber.setError("Enter a valid number");
            } else if (Integer.parseInt(edWalletAmount.getText().toString()) < 1) {
                edWalletAmount.setError("Enter a valid amount");
            } else {
                callWalletTransferApi();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            edWalletAmount.setError("Enter a valid amount");
        }
    }

    @OnClick(R.id.tvTransferBank)
    public void tvTransferBankClick() {
        try {
            if (edName.getText().toString().length() < 1) {
                edName.setError("Enter a name");
            } else if (edAccountNumber.getText().toString().length() < 6) {
                edAccountNumber.setError("Enter a valid account number");
            } else if (Integer.parseInt(edBankAmount.getText().toString()) < 1) {
                edBankAmount.setError("Enter a valid amount");
            } else {
                callBankTransferApi();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            edBankAmount.setError("Enter a valid amount");
        }
    }

    @OnClick(R.id.imgBackWallet)
    public void imgBackWalletClick() {
        llTransfer1.setVisibility(View.VISIBLE);
        llTransfer2.setVisibility(View.GONE);
    }

    @OnClick(R.id.imgBackBank)
    public void imgBackBankClick() {
        llTransfer1.setVisibility(View.VISIBLE);
        llTransfer3.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isWalletReacharged) {
            isWalletReacharged = false;
            callViewWalletBalanceApi();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.navBackButton)
    public void navBackButtonClick() {
        finish();
    }

    @OnClick(R.id.tvRecharge)
    public void tvRechargeClick() {
        Intent intent = new Intent(WalletActivity.this, PaymentActivity.class);
        startActivity(intent);
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
                            tvBalance.setText(response.body().getWallet_balance() + "");
                            refreshWallet.setRefreshing(false);
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

    private void callWalletTransferApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TransferAmountToWalletResponseModel> call = apiService.transferAmountToWallet(appPrefes.getData(Constants.USER_ID), userToSendMoney, edWalletAmount.getText().toString());
                call.enqueue(new retrofit2.Callback<TransferAmountToWalletResponseModel>() {
                    @Override
                    public void onResponse(Call<TransferAmountToWalletResponseModel> call, Response<TransferAmountToWalletResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                callViewWalletBalanceApi();
                                edPhoneNumber.setText("");
                                edWalletAmount.setText("");
                            } else {
                                Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(TRANSFER_WALLET_BALANCE_TO_WALLET_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<TransferAmountToWalletResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(TRANSFER_WALLET_BALANCE_TO_WALLET_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(TRANSFER_WALLET_BALANCE_TO_WALLET_API);
            }
        } else {
            showNoInternetAlert(TRANSFER_WALLET_BALANCE_TO_WALLET_API);
        }
    }

    private void callListBanksApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ListBanksResponseModel> call = apiService.listBanks("");
                call.enqueue(new retrofit2.Callback<ListBanksResponseModel>() {
                    @Override
                    public void onResponse(Call<ListBanksResponseModel> call, Response<ListBanksResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                bankNames = new String[response.body().getData().size()];
                                bankCodes = new String[response.body().getData().size()];
                                for (int i = 0; i < response.body().getData().size(); i++) {
                                    bankNames[i] = response.body().getData().get(i).getName();
                                    bankCodes[i] = response.body().getData().get(i).getCode();
                                }
                                ArrayAdapter aa = new ArrayAdapter(WalletActivity.this, android.R.layout.simple_spinner_item, bankNames);
                                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerBank.setAdapter(aa);
                                spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        selectedBankCode = bankCodes[i];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            } else {
                                Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(LIST_BANKS_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListBanksResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(LIST_BANKS_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(LIST_BANKS_API);
            }
        } else {
            showNoInternetAlert(LIST_BANKS_API);
        }
    }

    private void callBankTransferApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TransferAmountToBankResponseModel> call = apiService.transferAmountToBank(edName.getText().toString(), edAccountNumber.getText().toString(), selectedBankCode, edBankAmount.getText().toString(), appPrefes.getData(Constants.USER_ID));
                call.enqueue(new retrofit2.Callback<TransferAmountToBankResponseModel>() {
                    @Override
                    public void onResponse(Call<TransferAmountToBankResponseModel> call, Response<TransferAmountToBankResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                callViewWalletBalanceApi();
                                edName.setText("");
                                edAccountNumber.setText("");
                                edBankAmount.setText("");
                            } else {
                                Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(TRANSFER_WALLET_BALANCE_TO_BANK_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<TransferAmountToBankResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(TRANSFER_WALLET_BALANCE_TO_BANK_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(TRANSFER_WALLET_BALANCE_TO_BANK_API);
            }
        } else {
            showNoInternetAlert(TRANSFER_WALLET_BALANCE_TO_BANK_API);
        }
    }

    private void callCheckPhoneNumberApi() {
        if (cd.isConnectingToInternet()) {
            try {
                myProgressDialog.setProgress(false);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<CheckPhoneNumberResponseModel> call = apiService.checkPhoneNumber("wallet/check/" + edPhoneNumber.getText().toString());
                call.enqueue(new retrofit2.Callback<CheckPhoneNumberResponseModel>() {
                    @Override
                    public void onResponse(Call<CheckPhoneNumberResponseModel> call, Response<CheckPhoneNumberResponseModel> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (response.body().isStatus()) {
                                userToSendMoney = response.body().getData().getU_id() + "";
                                edNameUser.setText(response.body().getData().getU_first_name() + " " + response.body().getData().getU_last_name());
//                                callWalletTransferApi();
                            } else {
                                edPhoneNumber.setError("Phone number not found!");
                                Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showServerErrorAlert(CHECK_PHONE_NUMBER_API);
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPhoneNumberResponseModel> call, Throwable t) {
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t.toString());
                        showServerErrorAlert(CHECK_PHONE_NUMBER_API);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(CHECK_PHONE_NUMBER_API);
            }
        } else {
            showNoInternetAlert(CHECK_PHONE_NUMBER_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        switch (apiCode) {
            case VIEW_WALLET_BALANCE_API:
                callViewWalletBalanceApi();
                break;
            case TRANSFER_WALLET_BALANCE_TO_WALLET_API:
                callWalletTransferApi();
                break;
            case LIST_BANKS_API:
                callListBanksApi();
                break;
            case TRANSFER_WALLET_BALANCE_TO_BANK_API:
                callBankTransferApi();
                break;
            case CHECK_PHONE_NUMBER_API:
                callCheckPhoneNumberApi();
                break;
        }
    }
}
