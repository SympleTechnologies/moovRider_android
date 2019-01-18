package com.moovapp.riderapp.utils.retrofit.responseModels;

/**
 * Created by Lijo Mathew Theckanal on 23-Jul-18.
 */

public class SocialLoginResponseModel {


    /**
     * status : true
     * data : {"user_details":{"u_id":29,"u_first_name":"Lijo Mathew","u_image":".png","wallet_balance":{"balance":"0"}},"access_token":"VEJBMXRIa3VnNlRhY3RBS3Q2ak0xdDBIZmRQdkVHR2JXS29FM2NwRklYQT0=","user_pic_url":"http://themoovapp.com/manage/uploads/userpic/.png","user_pic_url_100":"http://themoovapp.com/manage/uploads/userpic/croped/100/.png","user_pic_url_200":"http://themoovapp.com/manage/uploads/userpic/croped/200/.png"}
     * message : login success
     * links : {"self":"http://themoovapp.com/api/v1/api/v1/auth/social_login","forgot_password":"http://themoovapp.com/api/v1/auth/forgot","register":"http://themoovapp.com/api/v1/auth/register"}
     */

    private boolean status;
    private DataEntity data;
    private String message;
    private LinksEntity links;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinksEntity getLinks() {
        return links;
    }

    public void setLinks(LinksEntity links) {
        this.links = links;
    }

    public static class DataEntity {
        /**
         * user_details : {"u_id":29,"u_first_name":"Lijo Mathew","u_image":".png","wallet_balance":{"balance":"0"}}
         * access_token : VEJBMXRIa3VnNlRhY3RBS3Q2ak0xdDBIZmRQdkVHR2JXS29FM2NwRklYQT0=
         * user_pic_url : http://themoovapp.com/manage/uploads/userpic/.png
         * user_pic_url_100 : http://themoovapp.com/manage/uploads/userpic/croped/100/.png
         * user_pic_url_200 : http://themoovapp.com/manage/uploads/userpic/croped/200/.png
         */

        private UserDetailsEntity user_details;
        private String access_token;
        private String user_pic_url;
        private String user_pic_url_100;
        private String user_pic_url_200;

        public UserDetailsEntity getUser_details() {
            return user_details;
        }

        public void setUser_details(UserDetailsEntity user_details) {
            this.user_details = user_details;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getUser_pic_url() {
            return user_pic_url;
        }

        public void setUser_pic_url(String user_pic_url) {
            this.user_pic_url = user_pic_url;
        }

        public String getUser_pic_url_100() {
            return user_pic_url_100;
        }

        public void setUser_pic_url_100(String user_pic_url_100) {
            this.user_pic_url_100 = user_pic_url_100;
        }

        public String getUser_pic_url_200() {
            return user_pic_url_200;
        }

        public void setUser_pic_url_200(String user_pic_url_200) {
            this.user_pic_url_200 = user_pic_url_200;
        }

        public static class UserDetailsEntity {
            /**
             * u_id : 29
             * u_first_name : Lijo Mathew
             * u_image : .png
             * wallet_balance : {"balance":"0"}
             */

            private int u_id;
            private String u_first_name;
            private String u_image;
            private WalletBalanceEntity wallet_balance;

            public int getU_id() {
                return u_id;
            }

            public void setU_id(int u_id) {
                this.u_id = u_id;
            }

            public String getU_first_name() {
                return u_first_name;
            }

            public void setU_first_name(String u_first_name) {
                this.u_first_name = u_first_name;
            }

            public String getU_image() {
                return u_image;
            }

            public void setU_image(String u_image) {
                this.u_image = u_image;
            }

//
//            public double getWallet_balance(){
//                return wallet_balance;
//            }
//
//            public void setWallet_balance(double wallet_balance) {
//                this.wallet_balance = wallet_balance;
//            }

//            public WalletBalanceEntity getWallet_balance() {
//                return wallet_balance;
//            }

//            public void setWallet_balance(WalletBalanceEntity wallet_balance) {
//                this.wallet_balance = wallet_balance;
//            }

            public static class WalletBalanceEntity {
                /**
                 * balance : 0
                 */

                private String balance;

                public String getBalance() {
                    return balance;
                }

                public void setBalance(String balance) {
                    this.balance = balance;
                }
            }
        }
    }

    public static class LinksEntity {
        /**
         * self : http://themoovapp.com/api/v1/api/v1/auth/social_login
         * forgot_password : http://themoovapp.com/api/v1/auth/forgot
         * register : http://themoovapp.com/api/v1/auth/register
         */

        private String self;
        private String forgot_password;
        private String register;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getForgot_password() {
            return forgot_password;
        }

        public void setForgot_password(String forgot_password) {
            this.forgot_password = forgot_password;
        }

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }
    }
}
