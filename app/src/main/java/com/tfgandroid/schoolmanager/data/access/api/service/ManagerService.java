/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 17:54 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ManagerService.java.
 * Last modified: 29/03/21 17:54.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class ManagerService {
  private static final String TAG = ManagerService.class.getSimpleName();
  private static ManagerService instanceService;
  private static ApiService apiService;

  public ManagerService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static ManagerService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new ManagerService(application);
    }

    return instanceService;
  }

  public List<Manager> getManagers(int school) throws ApiException {
    Response<List<Manager>> managerResponse;

    try {
      managerResponse = apiService.getManagerCall(school).execute();

      if (managerResponse.isSuccessful()) {
        return managerResponse.body();
      }

      throw TypeErrorConvert.parseError(managerResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.MANAGER, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
