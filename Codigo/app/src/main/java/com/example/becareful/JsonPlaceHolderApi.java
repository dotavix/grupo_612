package com.example.becareful;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("login")
    Call<RegisterResponse> createUserFromLogin(@Body RegisterRequest registro);

    @POST("register")
    Call<RegisterResponse> createUserFromRegister(@Body RegisterRequest registro);

    @POST("event")
    Call<EventResponse> createEvent(@Header("token") String token , @Body EventRequest evento);

}
