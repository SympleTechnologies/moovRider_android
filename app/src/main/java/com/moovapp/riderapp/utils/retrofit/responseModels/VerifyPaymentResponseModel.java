package com.moovapp.riderapp.utils.retrofit.responseModels;

/**
 * Created by Lijo Mathew Theckanal on 20-Jul-18.
 */

public class VerifyPaymentResponseModel {

    /**
     * status : true
     * message : Wallet Recharged Successfully
     * data : {"new_wallet_balance":80}
     */

    private boolean status;
    private String message;
    private DataEntity data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * new_wallet_balance : 80
         */

        private double new_wallet_balance;

        public double getNew_wallet_balance() {
            return new_wallet_balance;
        }

        public void setNew_wallet_balance(double new_wallet_balance) {
            this.new_wallet_balance = new_wallet_balance;
        }
    }
}
