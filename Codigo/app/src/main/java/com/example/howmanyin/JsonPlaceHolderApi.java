package com.example.howmanyin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("login")
    Call<RegistroResponse> createUserFromLogin(@Body Registracion registro);

    @POST("register")
    Call<RegistroResponse> createUserFromRegister(@Body Registracion registro);

}
