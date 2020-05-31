package com.example.becareful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

    private BroadcastReceiver MyReceiver = null;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        MyReceiver = new MyReceiver();

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }
}
