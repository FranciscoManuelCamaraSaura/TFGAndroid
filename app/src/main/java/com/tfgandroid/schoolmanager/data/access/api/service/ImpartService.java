/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 16:45 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ImpartService.java.
 * Last modified: 29/03/21 16:45.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Impart;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class ImpartService {
  private static final String TAG = ImpartService.class.getSimpleName();
  private static ImpartService instanceService;
  private static ApiService apiService;

  public ImpartService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static ImpartService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new ImpartService(application);
    }

    return instanceService;
  }

  public List<Impart> getImpart(int course_id, String group_words) throws ApiException {
    Response<List<Impart>> impartsResponse;

    try {
      impartsResponse = apiService.getImpartCall(course_id, group_words).execute();

      if (impartsResponse.isSuccessful()) {
        return impartsResponse.body();
      }

      throw TypeErrorConvert.parseError(impartsResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.COURSES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
