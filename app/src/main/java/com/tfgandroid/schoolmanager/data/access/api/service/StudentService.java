/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 16:39 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: StudentService.java.
 * Last modified: 29/03/21 16:39.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class StudentService {
  private static final String TAG = StudentService.class.getSimpleName();
  private static StudentService instanceService;
  private static ApiService apiService;

  public StudentService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static StudentService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new StudentService(application);
    }

    return instanceService;
  }

  public List<Student> getStudent(String legal_guardian) throws ApiException {
    Response<List<Student>> studentResponse;

    try {
      studentResponse = apiService.getStudentCall(legal_guardian).execute();

      if (studentResponse.isSuccessful()) {
        return studentResponse.body();
      }

      throw TypeErrorConvert.parseError(studentResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.STUDENT, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
