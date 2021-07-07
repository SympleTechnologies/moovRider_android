package com.moovapp.riderapp.utils.retrofit.responseModels;

/**
 * Created by Lijo Mathew Theckanal on 21-Jul-18.
 */

public class BookRideResponseModel {



    private boolean status;
    private DataEntity data;
    private String message;

//    @Override
//    public String toString() {
//        return "BookRideResponseModel{" +
//                "status=" + status +
//                ", data=" + data.toString() +
//                ", message='" + message.toString() + '\'' +
//                ", links=" + links +
//                ", poly_lines=" + poly_lines +
//                ", poly_line='" + poly_line + '\'' +
//                '}';
//    }

    private LinksEntity links;
    private PolyLinesEntity poly_lines;
    private String poly_line;

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

    public PolyLinesEntity getPoly_lines() {
        return poly_lines;
    }

    public void setPoly_lines(PolyLinesEntity poly_lines) {
        this.poly_lines = poly_lines;
    }

    public String getPoly_line() {
        return poly_line;
    }

    public void setPoly_line(String poly_line) {
        this.poly_line = poly_line;
    }

    public static class DataEntity {
        /**
         * driver_details : {"driver_id":2,"first_name":"remya","last_name":"Krishnan ","email":"remyakrishnankutty@gmail.com","institution_id":4,"institution_name":"University of Ilorin","phone":"9446172550","phone_country":"+91","gender":"female","vehicle_no":"Klo2-1092","verified":0,"u_device_id":"","car_model":"Hyundai Elantra","car_capacity":8,"license_no":"02/08/2018","license_expiry":"02/08/2018","dob":"02/08/2018","device_type":"android","ratings":0,"wallet_balance":"0","total_rides":211,"image":"http://themoovapp.com/manage/uploads/userpic/9227df6425b815c8.jpg"}
         * distance_to_drive_details : {"distance":"1 m","time":"1 min"}
         * trip_id : 375
         * ride_id : 560
         */

        private DriverDetailsEntity driver_details;
        private DistanceToDriveDetailsEntity distance_to_drive_details;
        private int trip_id;
        private int ride_id;

        public DriverDetailsEntity getDriver_details() {
            return driver_details;
        }

        public void setDriver_details(DriverDetailsEntity driver_details) {
            this.driver_details = driver_details;
        }

        public DistanceToDriveDetailsEntity getDistance_to_drive_details() {
            return distance_to_drive_details;
        }

        public void setDistance_to_drive_details(DistanceToDriveDetailsEntity distance_to_drive_details) {
            this.distance_to_drive_details = distance_to_drive_details;
        }

        public int getTrip_id() {
            return trip_id;
        }

        public void setTrip_id(int trip_id) {
            this.trip_id = trip_id;
        }

        public int getRide_id() {
            return ride_id;
        }

        public void setRide_id(int ride_id) {
            this.ride_id = ride_id;
        }

//        @Override
//        public String toString() {
//            return "DataEntity{" +
//                    "driver_details=" + driver_details.toString() +
//                    ", distance_to_drive_details=" + distance_to_drive_details.toString() +
//                    ", trip_id=" + trip_id +
//                    ", ride_id=" + ride_id +
//                    '}';
//        }

        public static class DriverDetailsEntity {
            /**
             * driver_id : 2
             * first_name : remya
             * last_name : Krishnan
             * email : remyakrishnankutty@gmail.com
             * institution_id : 4
             * institution_name : University of Ilorin
             * phone : 9446172550
             * phone_country : +91
             * gender : female
             * vehicle_no : Klo2-1092
             * verified : 0
             * u_device_id :
             * car_model : Hyundai Elantra
             * car_capacity : 8
             * license_no : 02/08/2018
             * license_expiry : 02/08/2018
             * dob : 02/08/2018
             * device_type : android
             * ratings : 0
             * wallet_balance : 0
             * total_rides : 211
             * image : http://themoovapp.com/manage/uploads/userpic/9227df6425b815c8.jpg
             */

            private String driver_id;
            private String first_name;
            private String last_name;
            private String email;
            private String institution_id;
            private String institution_name;
            private String phone;
            private String phone_country;
            private String gender;
            private String vehicle_no;
            private String verified;
            private String u_device_id;
            private String car_model;
            private String car_capacity;
            private String license_no;
            private String license_expiry;
            private String dob;
            private String device_type;
            private double ratings;
            private String wallet_balance;
            private String car_colour;

            public String getCar_colour() {
                return car_colour;
            }

            public void setCar_colour(String car_colour) {
                this.car_colour = car_colour;
            }

            public String getCar_image() {
                return car_image;
            }

            public void setCar_image(String car_image) {
                this.car_image = car_image;
            }

            private String car_image;
            private int total_rides;
            private String image;

            public String getDriver_id() {
                return driver_id;
            }

            public void setDriver_id(String driver_id) {
                this.driver_id = driver_id;
            }

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

            public String getInstitution_id() {
                return institution_id;
            }

            public void setInstitution_id(String institution_id) {
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

            public String getVerified() {
                return verified;
            }

            public void setVerified(String verified) {
                this.verified = verified;
            }

            public String getU_device_id() {
                return u_device_id;
            }

            public void setU_device_id(String u_device_id) {
                this.u_device_id = u_device_id;
            }

            public String getCar_model() {
                return car_model;
            }

            public void setCar_model(String car_model) {
                this.car_model = car_model;
            }

            public String getCar_capacity() {
                return car_capacity;
            }

            public void setCar_capacity(String car_capacity) {
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

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
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

            public int getTotal_rides() {
                return total_rides;
            }

            public void setTotal_rides(int total_rides) {
                this.total_rides = total_rides;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

//            @Override
//            public String toString() {
//                return "DriverDetailsEntity{" +
//                        "driver_id=" + driver_id +
//                        ", first_name='" + first_name + '\'' +
//                        ", last_name='" + last_name + '\'' +
//                        ", email='" + email + '\'' +
//                        ", institution_id=" + institution_id +
//                        ", institution_name='" + institution_name + '\'' +
//                        ", phone='" + phone + '\'' +
//                        ", phone_country='" + phone_country + '\'' +
//                        ", gender='" + gender + '\'' +
//                        ", vehicle_no='" + vehicle_no + '\'' +
//                        ", verified=" + verified +
//                        ", u_device_id='" + u_device_id + '\'' +
//                        ", car_model='" + car_model + '\'' +
//                        ", car_capacity=" + car_capacity +
//                        ", license_no='" + license_no + '\'' +
//                        ", license_expiry='" + license_expiry + '\'' +
//                        ", dob='" + dob + '\'' +
//                        ", device_type='" + device_type + '\'' +
//                        ", ratings=" + ratings +
//                        ", wallet_balance='" + wallet_balance + '\'' +
//                        ", total_rides=" + total_rides +
//                        ", image='" + image + '\'' +
//                        '}';
//            }
        }

        public static class DistanceToDriveDetailsEntity {
            /**
             * distance : 1 m
             * time : 1 min
             */

            private String distance;
            private String time;

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }

    public static class LinksEntity {
        /**
         * self : http://themoovapp.com/api/v2/api/v2/ride/book_now
         */

        private String self;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }
    }

    public static class PolyLinesEntity {
        /**
         * start : {"lat":8.57117,"lng":76.86625000000001}
         * end : {"lat":9.28372,"lng":79.1538}
         */

        private StartEntity start;
        private EndEntity end;

        public StartEntity getStart() {
            return start;
        }

        public void setStart(StartEntity start) {
            this.start = start;
        }

        public EndEntity getEnd() {
            return end;
        }

        public void setEnd(EndEntity end) {
            this.end = end;
        }

        public static class StartEntity {
            /**
             * lat : 8.57117
             * lng : 76.86625000000001
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        public static class EndEntity {
            /**
             * lat : 9.28372
             * lng : 79.1538
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
}
