/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 16:48 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: GroupHavePreceptorService.java.
 * Last modified: 29/03/21 16:48.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Teacher;
import com.tfgandroid.schoolmanager.data.enums.Degree;
import java.io.IOException;
import retrofit2.Response;

public class TeacherService {
  private static final String TAG = TeacherService.class.getSimpleName();
  private static TeacherService instanceService;
  private static ApiService apiService;

  public TeacherService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static TeacherService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new TeacherService(application);
    }

    return instanceService;
  }

  public Teacher getGroupHavePreceptorCall(
      Character group_words, Integer course_number, Degree course_degree) throws ApiException {
    Response<Teacher> teacherResponse;

    try {
      teacherResponse =
          apiService.getGroupHavePreceptorCall(group_words, course_number, course_degree).execute();

      if (teacherResponse.isSuccessful()) {
        return teacherResponse.body();
      }

      throw TypeErrorConvert.parseError(teacherResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.TEACHER, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
