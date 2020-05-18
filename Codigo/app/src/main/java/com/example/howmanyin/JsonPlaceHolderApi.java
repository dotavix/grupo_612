package com.example.howmanyin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("login")
    Call<RegistroResponse> createUserFromLogin(@Body Registracion registro);

    @POST("register")
    Call<RegistroResponse> createUserFromRegister(@Body Registracion registro);

    @POST("event")
    Call<EventResponse> createEvent(@Header("token") String token , @Body EventRequest evento);

}
