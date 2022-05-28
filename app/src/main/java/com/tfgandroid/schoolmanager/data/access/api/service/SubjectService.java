/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 16:58 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectService.java.
 * Last modified: 29/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class SubjectService {
  private static final String TAG = SubjectService.class.getSimpleName();
  private static SubjectService instanceService;
  private static ApiService apiService;

  public SubjectService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static SubjectService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new SubjectService(application);
    }

    return instanceService;
  }

  public List<Subject> getSubjects(String code) throws ApiException {
    Response<List<Subject>> subjectsResponse;

    try {
      subjectsResponse = apiService.getSubjectsCall(code).execute();

      if (subjectsResponse.isSuccessful()) {
        return subjectsResponse.body();
      }

      throw TypeErrorConvert.parseError(subjectsResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.SUBJECT, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
