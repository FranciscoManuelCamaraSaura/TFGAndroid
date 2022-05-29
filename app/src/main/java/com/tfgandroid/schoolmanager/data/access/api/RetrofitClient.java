/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/03/21 20:32 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ApiAdapter.java.
 * Last modified: 22/03/21 20:32.
 */

package com.tfgandroid.schoolmanager.data.access.api;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
  private static final int TIMEOUT = 15;
  private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  private static final String REMOTE_URL = "http://cryptic-thicket-59173.herokuapp.com/api/";
  private static Retrofit retrofitInstance;

  private static String token;
  private static SharedPreferences sharedPreferences;

  public static Retrofit getRetrofitClient() {
    if (retrofitInstance == null) {
      OkHttpClient okHttpClient =
          new OkHttpClient.Builder()
              .retryOnConnectionFailure(true)
              .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
              .build();

      Gson gson = new GsonBuilder().setLenient().setDateFormat(DATE_FORMAT).create();

      retrofitInstance =
          new Retrofit.Builder()
              .baseUrl(REMOTE_URL)
              .addConverterFactory(GsonConverterFactory.create(gson))
              .client(okHttpClient)
              .build();
    }

    return retrofitInstance;
  }

  public static ApiService getApiService(Application application) {
    if (sharedPreferences == null) {
      sharedPreferences =
          PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());
    }

    Retrofit retrofit = getRetrofitClient();

    return retrofit.create(ApiService.class);
  }

  public static String getToken() {
    return token;
  }

  public static void setToken(String token) {
    RetrofitClient.token = token;
  }
}
