package com.example.becareful;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://so-unlam.net.ar/api/api/";

    private static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static Retrofit getClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}

