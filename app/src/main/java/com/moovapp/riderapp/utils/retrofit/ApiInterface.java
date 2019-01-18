package com.moovapp.riderapp.utils.retrofit;


import com.moovapp.riderapp.utils.placesAutocomplete.AddDriverRatingResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.BookFutureRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.BookRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.CancelRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.CheckEmailResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.CheckPhoneNumberResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ForgotPasswordResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.InitPaymentResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ListBanksResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.LoginEmailResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.PaymentResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.RegistartionResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.RegisterModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.RequestOtpResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.RideSearchResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.SelectCollegeResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.SelectUserTypeResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.SocialLoginResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.TalkToUsResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.TransferAmountToBankResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.TransferAmountToWalletResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdateEmailResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdatePasswordResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdatePhoneResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdateProfilePicResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.UpdateProfileResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.VerifyPaymentResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCollegesResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCurrentRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewDriverDetailsResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewPreviousRidesResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewProfileResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewWalletBalanceResponseModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Lijo Mathew Theckanal on 04-Aug-16.
 */
public interface ApiInterface {

    @Multipart
    @POST("auth/register")
//    Call<RegistartionResponseModel> register(@Body RegisterModel registerModel);
    Call<RegistartionResponseModel> register(@PartMap Map<String, RequestBody> params);

    @GET("auth/select_college")
    Call<SelectCollegeResponseModel> selectCollege(@Query("dummy") String dummy);

    @GET("auth/select_user_type")
    Call<SelectUserTypeResponseModel> selectUserType(@Query("dummy") String dummy);

    @FormUrlEncoded
    @POST("auth/check/email")
    Call<CheckEmailResponseModel> checkEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("auth/login/email")
    Call<LoginEmailResponseModel> loginEmail(@Field("email") String email,
                                             @Field("password") String password,
                                             @Field("device_type") String device_type,
                                             @Field("device_id") String device_id,
                                             @Field("push_token") String push_token,
                                             @Field("app_version") String app_version);

    @FormUrlEncoded
    @POST("auth/social_login")
    Call<SocialLoginResponseModel> socialLogin(@Field("provider") String provider,
                                               @Field("uid") String uid,
                                               @Field("device_type") String device_type,
                                               @Field("device_id") String device_id,
                                               @Field("app_version") String app_version);

    @FormUrlEncoded
    @POST("auth/forgot")
    Call<ForgotPasswordResponseModel> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("support/email")
    Call<TalkToUsResponseModel> talkToUs(@Field("userid") String userid,
                                         @Field("from") String from,
                                         @Field("subject") String subject,
                                         @Field("message") String message);

    @GET
    Call<RideSearchResponseModel> rideSearch(@Url String url);

    @GET
    Call<ViewCollegesResponseModel> viewColleges(@Url String url);

    @GET
    Call<ViewWalletBalanceResponseModel> viewWalletBalance(@Url String url);

    @FormUrlEncoded
    @POST("wallet/transfer/user")
    Call<TransferAmountToWalletResponseModel> transferAmountToWallet(@Field("from") String from,
                                                                     @Field("to") String to,
                                                                     @Field("amount") String amount);
    @GET
    Call<CheckPhoneNumberResponseModel> checkPhoneNumber(@Url String url);

    @FormUrlEncoded
    @POST("wallet/transfer/bank")
    Call<TransferAmountToBankResponseModel> transferAmountToBank(@Field("name") String name,
                                                                 @Field("account_number") String account_number,
                                                                 @Field("bank_code") String bank_code,
                                                                 @Field("amount") String amount,
                                                                 @Field("userid") String userid);

    @GET("wallet/banks")
    Call<ListBanksResponseModel> listBanks(@Query("dummy") String dummy);

    @FormUrlEncoded
    @POST("wallet/init_payment")
    Call<InitPaymentResponseModel> initPayment(@Field("amount") String amount,
                                               @Field("userid") String userid);

    @FormUrlEncoded
    @POST("wallet/verify")
    Call<VerifyPaymentResponseModel> verifyPayment(@Field("reference") String reference,
                                                   @Field("userid") String userid);

    @GET
    Call<ViewProfileResponseModel> viewProfile(@Url String url);

    @Multipart
    @POST("update/profile_pic")
    Call<UpdateProfilePicResponseModel> updateProfilePic(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("update/profile")
    Call<UpdateProfileResponseModel> updateProfile(@Field("userid") String userid,
                                                   @Field("f_name") String f_name,
                                                   @Field("l_name") String l_name,
                                                   @Field("college") String college,
                                                   @Field("gender") String gender);

    @FormUrlEncoded
    @POST("update/email")
    Call<UpdateEmailResponseModel> updateEmail(@Field("userid") String userid,
                                               @Field("old_email") String old_email,
                                               @Field("new_email") String new_email);

    @FormUrlEncoded
    @POST("update/password")
    Call<UpdatePasswordResponseModel> updatePassword(@Field("userid") String userid,
                                                     @Field("old_password") String old_password,
                                                     @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("ride/book_now")
    Call<BookRideResponseModel> bookRide(@Field("userid") String userid,
                                         @Field("from") String from,
                                         @Field("from_lat") String from_lat,
                                         @Field("from_long") String from_long,
                                         @Field("to") String to,
                                         @Field("to_lat") String to_lat,
                                         @Field("to_long") String to_long,
                                         @Field("pooling") String pooling,
                                         @Field("seats") String seats,
                                         @Field("collegeid") String collegeid,
                                         @Field("amount") String amount,
                                         @Field("current_lat") String current_lat,
                                         @Field("current_long") String current_long);

    @FormUrlEncoded
    @POST("ride/book_future")
    Call<BookFutureRideResponseModel> bookFutureRide(@Field("userid") String userid,
                                                     @Field("from") String from,
                                                     @Field("from_lat") String from_lat,
                                                     @Field("from_long") String from_long,
                                                     @Field("to") String to,
                                                     @Field("to_lat") String to_lat,
                                                     @Field("to_long") String to_long,
                                                     @Field("pooling") String pooling,
                                                     @Field("seats") String seats,
                                                     @Field("collegeid") String collegeid,
                                                     @Field("amount") String amount,
                                                     @Field("current_lat") String current_lat,
                                                     @Field("current_long") String current_long,
                                                     @Field("booking_to_date") String booking_to_date,
                                                     @Field("booking_to_time") String booking_to_time);

    @FormUrlEncoded
    @POST("update/phone/otp")
    Call<RequestOtpResponseModel> requestOtp(@Field("userid") String userid,
                                             @Field("phone_country") String phone_country,
                                             @Field("phone") String phone);

    @FormUrlEncoded
    @POST("update/phone")
    Call<UpdatePhoneResponseModel> updatePhone(@Field("userid") String userid,
                                               @Field("phone_country") String phone_country,
                                               @Field("phone") String phone,
                                               @Field("otp") String otp);

    @FormUrlEncoded
    @POST("ride/add_rating")
    Call<AddDriverRatingResponseModel> addDriverRating(@Field("driver_id") String driver_id,
                                                       @Field("trip_id") String trip_id,
                                                       @Field("user_id") String user_id,
                                                       @Field("rating_score") String rating_score,
                                                       @Field("review") String review);

    @FormUrlEncoded
    @POST("rides/pay")
    Call<PaymentResponseModel> pay(@Field("userid") String userid,
                                   @Field("ride_id") String ride_id,
                                   @Field("amount") String amount);

    @GET
    Call<CancelRideResponseModel> cancelRide(@Url String url);

    @GET
    Call<ViewPreviousRidesResponseModel> viewPreviousRide(@Url String url);

    @GET
    Call<ViewCurrentRideResponseModel> viewCurrentRide(@Url String url);

    @GET
    Call<ViewDriverDetailsResponseModel> viewDriverDetails(@Url String url);
}
