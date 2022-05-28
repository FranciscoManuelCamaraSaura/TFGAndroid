/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 24/03/21 19:26 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: LegalGuardianService.java.
 * Last modified: 24/03/21 19:26.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import java.io.IOException;
import retrofit2.Response;

public class LegalGuardianService {
  private static final String TAG = LegalGuardianService.class.getSimpleName();
  private static LegalGuardianService instanceService;
  private static ApiService apiService;

  public LegalGuardianService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static LegalGuardianService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new LegalGuardianService(application);
    }

    return instanceService;
  }

  public LegalGuardian loginCall(Login login) throws ApiException {
    Response<LegalGuardian> loginResponse;

    try {
      loginResponse = apiService.loginCall(login).execute();

      if (loginResponse.isSuccessful()) {
        return loginResponse.body();
      }

      throw TypeErrorConvert.parseError(loginResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.LOGIN, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public LegalGuardian updateLegalGuardianCall(LegalGuardian legalGuardian) throws ApiException {
    Response<LegalGuardian> legalGuardianResponse;

    try {
      legalGuardianResponse = apiService.updateLegalGuardianCall(legalGuardian).execute();

      if (legalGuardianResponse.isSuccessful()) {
        return legalGuardianResponse.body();
      }

      throw TypeErrorConvert.parseError(legalGuardianResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.LEGAL_GUARDIAN, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
