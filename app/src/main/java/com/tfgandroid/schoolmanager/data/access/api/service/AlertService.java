/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 18:19 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertService.java.
 * Last modified: 29/5/22 13:19.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;

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
}
