package com.moovapp.riderapp.utils.retrofit;

import com.google.gson.GsonBuilder;
import com.moovapp.riderapp.utils.Constants;
import com.moovapp.riderapp.utils.retrofit.responseModels.NullOnEmptyConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Lijo Mathew Theckanal on 04-Aug-16.
 */
public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HEROKU_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .build();
        }
        return retrofit;
    }


}


