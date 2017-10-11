package com.hintdesk.android.aspnetwebapi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ServusKevin on 07.10.2017.
 */

public interface SampleApi {
    @FormUrlEncoded
    @POST("Token")
    Call<TokenModel> Login(@Field("grant_type") String grant_type, @Field("username") String email, @Field("password") String password);

    @POST("api/Account/Register")
    Call<String> Register(@Body RegisterBindingModel model);

    @GET("api/values")
    Call<String[]> GetValues(@Header("Authorization") String authorization);
}

