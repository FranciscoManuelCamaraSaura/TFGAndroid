/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/03/21 19:25 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SchoolService.java.
 * Last modified: 26/03/21 19:25.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class SchoolService {
  private static final String TAG = SchoolService.class.getSimpleName();
  private static SchoolService instanceService;
  private static ApiService apiService;

  public SchoolService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static SchoolService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new SchoolService(application);
    }

    return instanceService;
  }

  public School getSchool(Integer id) throws ApiException {
    Response<School> schoolResponse;

    try {
      schoolResponse = apiService.getSchoolCall(id).execute();

      if (schoolResponse.isSuccessful()) {
        return schoolResponse.body();
      }

      throw TypeErrorConvert.parseError(schoolResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.SCHOOL, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public List<School> getAllSchools() throws ApiException {
    Response<List<School>> schoolsResponse;

    try {
      schoolsResponse = apiService.getSchoolsCall().execute();

      if (schoolsResponse.isSuccessful()) {
        return schoolsResponse.body();
      }

      throw TypeErrorConvert.parseError(schoolsResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.SCHOOLS, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
