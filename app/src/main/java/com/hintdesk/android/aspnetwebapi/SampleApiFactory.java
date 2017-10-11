package com.hintdesk.android.aspnetwebapi;

import android.icu.text.DisplayContext;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ServusKevin on 07.10.2017.
 */



public class SampleApiFactory {
    private static SampleApi instance = null;
    public static SampleApi getInstance() {
        if (instance == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://aspnetwebapiandaspnetidentity.apphb.com/")
                    .client(client)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(SampleApi.class);
        }
        return instance;
    }
}