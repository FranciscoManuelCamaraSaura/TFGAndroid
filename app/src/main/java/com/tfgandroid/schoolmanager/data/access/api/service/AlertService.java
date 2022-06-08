/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 18:19 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertService.java.
 * Last modified: 29/5/22 13:19.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class AlertService {
  private static final String TAG = AlertService.class.getSimpleName();
  private static AlertService instanceService;
  private static ApiService apiService;

  public AlertService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static AlertService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new AlertService(application);
    }

    return instanceService;
  }

  public List<Alert> getReceivedAlerts(String received) throws ApiException {
    Response<List<Alert>> alertsResponse;

    try {
      alertsResponse = apiService.getReceivedAlertsCall(received).execute();

      if (alertsResponse.isSuccessful()) {
        return alertsResponse.body();
      }

      throw TypeErrorConvert.parseError(alertsResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.RECEIVED_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public Alert readAlert(Alert alert) throws ApiException {
    Response<Alert> alertResponse;

    try {
      alertResponse = apiService.setReadAlert(alert.getId()).execute();

      if (alertResponse.isSuccessful()) {
        return alertResponse.body();
      }

      throw TypeErrorConvert.parseError(alertResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.NEW_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
