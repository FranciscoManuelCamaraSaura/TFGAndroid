/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/2/22 11:13 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MakesService.java.
 * Last modified: 5/6/22 0:13.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Makes;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class MakesService {
  private static final String TAG = MakesService.class.getSimpleName();
  private static MakesService instanceService;
  private static ApiService apiService;

  public MakesService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static MakesService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new MakesService(application);
    }

    return instanceService;
  }

  public List<Makes> getNotes(int student) throws ApiException {
    Response<List<Makes>> makesResponse;

    try {
      makesResponse = apiService.getMakesCall(student).execute();

      if (makesResponse.isSuccessful()) {
        return makesResponse.body();
      }

      throw TypeErrorConvert.parseError(makesResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.EXAMS, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
