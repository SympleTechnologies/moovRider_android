package com.moovapp.riderapp.utils.retrofit.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("f_name")
    @Expose
    public String f_name;
    @SerializedName("l_name")
    @Expose
    public String l_name;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("push_token")
    @Expose
    public String push_token;
    @SerializedName("app_version")
    @Expose
    public String app_version;
    @SerializedName("device_type")
    @Expose
    public String device_type;
    @SerializedName("device_id")
    @Expose
    public String device_id;
    @SerializedName("auth_token")
    @Expose
    public String auth_token;
    @SerializedName("auth_provider")
    @Expose
    public String auth_provider;
    @SerializedName("phone_country")
    @Expose
    public String phone_country;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("user_type")
    @Expose
    public String user_type;
    @SerializedName("gender")
    @Expose
    public String gender;

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPush_token() {
        return push_token;
    }

    public void setPush_token(String push_token) {
        this.push_token = push_token;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getAuth_provider() {
        return auth_provider;
    }

    public void setAuth_provider(String auth_provider) {
        this.auth_provider = auth_provider;
    }

    public String getPhone_country() {
        return phone_country;
    }

    public void setPhone_country(String phone_country) {
        this.phone_country = phone_country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuth_uid() {
        return auth_uid;
    }

    public void setAuth_uid(String auth_uid) {
        this.auth_uid = auth_uid;
    }

    public String getAuth_mode() {
        return auth_mode;
    }

    public void setAuth_mode(String auth_mode) {
        this.auth_mode = auth_mode;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String password;
    public String auth_uid;
    public String auth_mode;
    public String college;



}
