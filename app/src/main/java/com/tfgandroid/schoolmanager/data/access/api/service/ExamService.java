/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 17:01 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ExamService.java.
 * Last modified: 29/03/21 17:01.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class ExamService {
  private static final String TAG = ExamService.class.getSimpleName();
  private static ExamService instanceService;
  private static ApiService apiService;

  public ExamService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static ExamService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new ExamService(application);
    }

    return instanceService;
  }

  public List<Exam> getExamsCall(Integer exam) throws ApiException {
    Response<List<Exam>> examsResponse;

    try {
      examsResponse = apiService.getExamsCall(exam).execute();

      if (examsResponse.isSuccessful()) {
        return examsResponse.body();
      }

      throw TypeErrorConvert.parseError(examsResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.EXAMS, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
