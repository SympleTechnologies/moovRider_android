package com.moovapp.riderapp.utils.retrofit.responseModels;

/**
 * Created by Lijo Mathew Theckanal on 26-Jul-18.
 */

public class ViewDriverDetailsResponseModel {

    /**
     * status : true
     * data : {"user_details":{"first_name":"test2","last_name":"user","email":"testd31@test.com","institution_id":1,"institution_name":"sample school 1","phone":"888888888","phone_country":"+91","gender":"male","vehicle_no":"KL 123456","verified":0,"car_model":1,"car_capacity":3,"license_no":"15-05-2018","license_expiry":"15-05-2018","dob":"15-05-2018","ratings":0,"wallet_balance":"2000","image":"e7dddb5772b94ceb.jpg"},"user_pic_url":"http://themoovapp.com/manage/uploads/userpic/e7dddb5772b94ceb.jpg","user_pic_url_100":"http://themoovapp.com/manage/uploads/userpic/croped/100/e7dddb5772b94ceb.jpg","user_pic_url_200":"http://themoovapp.com/manage/uploads/userpic/croped/200/e7dddb5772b94ceb.jpg"}
     * message : Driver detais
     * links : {"self":"http://themoovapp.com/api/v1/api/v1/view/details/driver/17"}
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
         * user_details : {"first_name":"test2","last_name":"user","email":"testd31@test.com","institution_id":1,"institution_name":"sample school 1","phone":"888888888","phone_country":"+91","gender":"male","vehicle_no":"KL 123456","verified":0,"car_model":1,"car_capacity":3,"license_no":"15-05-2018","license_expiry":"15-05-2018","dob":"15-05-2018","ratings":0,"wallet_balance":"2000","image":"e7dddb5772b94ceb.jpg"}
         * user_pic_url : http://themoovapp.com/manage/uploads/userpic/e7dddb5772b94ceb.jpg
         * user_pic_url_100 : http://themoovapp.com/manage/uploads/userpic/croped/100/e7dddb5772b94ceb.jpg
         * user_pic_url_200 : http://themoovapp.com/manage/uploads/userpic/croped/200/e7dddb5772b94ceb.jpg
         */

        private UserDetailsEntity user_details;
        private String user_pic_url;
        private String user_pic_url_100;
        private String user_pic_url_200;

        public UserDetailsEntity getUser_details() {
            return user_details;
        }

        public void setUser_details(UserDetailsEntity user_details) {
            this.user_details = user_details;
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
             * first_name : test2
             * last_name : user
             * email : testd31@test.com
             * institution_id : 1
             * institution_name : sample school 1
             * phone : 888888888
             * phone_country : +91
             * gender : male
             * vehicle_no : KL 123456
             * verified : 0
             * car_model : 1
             * car_capacity : 3
             * license_no : 15-05-2018
             * license_expiry : 15-05-2018
             * dob : 15-05-2018
             * ratings : 0
             * wallet_balance : 2000
             * image : e7dddb5772b94ceb.jpg
             */

            private String first_name;
            private String last_name;
            private String email;
            private int institution_id;
            private String institution_name;
            private String phone;
            private String phone_country;
            private String gender;
            private String vehicle_no;
            private int verified;
            private int car_model;
            private int car_capacity;
            private String license_no;
            private String license_expiry;
            private String dob;
            private double ratings;
            private String wallet_balance;
            private String image;

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getInstitution_id() {
                return institution_id;
            }

            public void setInstitution_id(int institution_id) {
                this.institution_id = institution_id;
            }

            public String getInstitution_name() {
                return institution_name;
            }

            public void setInstitution_name(String institution_name) {
                this.institution_name = institution_name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhone_country() {
                return phone_country;
            }

            public void setPhone_country(String phone_country) {
                this.phone_country = phone_country;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getVehicle_no() {
                return vehicle_no;
            }

            public void setVehicle_no(String vehicle_no) {
                this.vehicle_no = vehicle_no;
            }

            public int getVerified() {
                return verified;
            }

            public void setVerified(int verified) {
                this.verified = verified;
            }

            public int getCar_model() {
                return car_model;
            }

            public void setCar_model(int car_model) {
                this.car_model = car_model;
            }

            public int getCar_capacity() {
                return car_capacity;
            }

            public void setCar_capacity(int car_capacity) {
                this.car_capacity = car_capacity;
            }

            public String getLicense_no() {
                return license_no;
            }

            public void setLicense_no(String license_no) {
                this.license_no = license_no;
            }

            public String getLicense_expiry() {
                return license_expiry;
            }

            public void setLicense_expiry(String license_expiry) {
                this.license_expiry = license_expiry;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public double getRatings() {
                return ratings;
            }

            public void setRatings(double ratings) {
                this.ratings = ratings;
            }

            public String getWallet_balance() {
                return wallet_balance;
            }

            public void setWallet_balance(String wallet_balance) {
                this.wallet_balance = wallet_balance;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }

    public static class LinksEntity {
        /**
         * self : http://themoovapp.com/api/v1/api/v1/view/details/driver/17
         */

        private String self;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }
    }
}
