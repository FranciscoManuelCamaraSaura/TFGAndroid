/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/03/21 19:00 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventService.java.
 * Last modified: 26/03/21 19:00.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class EventService {
  private static final String TAG = EventService.class.getSimpleName();
  private static EventService instanceService;
  private static ApiService apiService;

  public EventService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static EventService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new EventService(application);
    }

    return instanceService;
  }

  public List<Event> getEventsCall(int school, int student) throws ApiException {
    Response<List<Event>> eventResponse;

    try {
      eventResponse = apiService.getEventsCall(school, student).execute();

      if (eventResponse.isSuccessful()) {
        return eventResponse.body();
      }

      throw TypeErrorConvert.parseError(eventResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.EVENTS, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
