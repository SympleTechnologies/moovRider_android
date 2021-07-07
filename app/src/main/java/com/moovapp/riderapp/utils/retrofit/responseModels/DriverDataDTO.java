package com.moovapp.riderapp.utils.retrofit.responseModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DriverDataDTO implements Serializable {

	@SerializedName("driver_id")
	private String driverId;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("email")
	private String email;

	@SerializedName("institution_id")
	private String institutionId;

	@SerializedName("institution_name")
	private String institutionName;

	@SerializedName("phone")
	private String phone;

	@SerializedName("phone_country")
	private String phoneCountry;

	@SerializedName("gender")
	private String gender;

	@SerializedName("vehicle_no")
	private String vehicleNo;

	@SerializedName("verified")
	private String verified;

	@SerializedName("u_device_id")
	private String uDeviceId;

	@SerializedName("car_model")
	private String carModel;

	@SerializedName("car_capacity")
	private String carCapacity;

	@SerializedName("license_no")
	private String licenseNo;

	@SerializedName("license_expiry")
	private String licenseExpiry;

	@SerializedName("dob")
	private String dob;

	@SerializedName("ratings")
	private double ratings;

	@SerializedName("wallet_balance")
	private String walletBalance;

	@SerializedName("image")
	private String image;

	public void setDriverId(String driverId){
		this.driverId = driverId;
	}

	public String getDriverId(){
		return driverId;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setInstitutionId(String institutionId){
		this.institutionId = institutionId;
	}

	public String getInstitutionId(){
		return institutionId;
	}

	public void setInstitutionName(String institutionName){
		this.institutionName = institutionName;
	}

	public String getInstitutionName(){
		return institutionName;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setPhoneCountry(String phoneCountry){
		this.phoneCountry = phoneCountry;
	}

	public String getPhoneCountry(){
		return phoneCountry;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleNo(){
		return vehicleNo;
	}

	public void setVerified(String verified){
		this.verified = verified;
	}

	public String getVerified(){
		return verified;
	}

	public void setUDeviceId(String uDeviceId){
		this.uDeviceId = uDeviceId;
	}

	public String getUDeviceId(){
		return uDeviceId;
	}

	public void setCarModel(String carModel){
		this.carModel = carModel;
	}

	public String getCarModel(){
		return carModel;
	}

	public void setCarCapacity(String carCapacity){
		this.carCapacity = carCapacity;
	}

	public String getCarCapacity(){
		return carCapacity;
	}

	public void setLicenseNo(String licenseNo){
		this.licenseNo = licenseNo;
	}

	public String getLicenseNo(){
		return licenseNo;
	}

	public void setLicenseExpiry(String licenseExpiry){
		this.licenseExpiry = licenseExpiry;
	}

	public String getLicenseExpiry(){
		return licenseExpiry;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setRatings(double ratings){
		this.ratings = ratings;
	}

	public double getRatings(){
		return ratings;
	}

	public void setWalletBalance(String walletBalance){
		this.walletBalance = walletBalance;
	}

	public String getWalletBalance(){
		return walletBalance;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	@Override
 	public String toString(){
		return 
			"DriverDataDTO{" + 
			"driver_id = '" + driverId + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",email = '" + email + '\'' + 
			",institution_id = '" + institutionId + '\'' + 
			",institution_name = '" + institutionName + '\'' + 
			",phone = '" + phone + '\'' + 
			",phone_country = '" + phoneCountry + '\'' + 
			",gender = '" + gender + '\'' + 
			",vehicle_no = '" + vehicleNo + '\'' + 
			",verified = '" + verified + '\'' + 
			",u_device_id = '" + uDeviceId + '\'' + 
			",car_model = '" + carModel + '\'' + 
			",car_capacity = '" + carCapacity + '\'' + 
			",license_no = '" + licenseNo + '\'' + 
			",license_expiry = '" + licenseExpiry + '\'' + 
			",dob = '" + dob + '\'' + 
			",ratings = '" + ratings + '\'' + 
			",wallet_balance = '" + walletBalance + '\'' + 
			",image = '" + image + '\'' + 
			"}";
		}
}