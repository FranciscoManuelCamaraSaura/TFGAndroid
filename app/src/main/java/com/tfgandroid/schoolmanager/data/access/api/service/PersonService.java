/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 14/8/21 16:12 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: PersonService.java.
 * Last modified: 14/8/21 16:12.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class PersonService {
  private static final String TAG = PersonService.class.getSimpleName();
  private static PersonService instanceService;
  private static ApiService apiService;

  public PersonService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static PersonService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new PersonService(application);
    }

    return instanceService;
  }

  public Person getPerson(String dni) throws ApiException {
    Response<Person> personResponse;

    try {
      personResponse = apiService.getPerson(dni).execute();

      if (personResponse.isSuccessful()) {
        return personResponse.body();
      }

      throw TypeErrorConvert.parseError(personResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.SCHOOL, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public Person setPerson(Person person) throws ApiException {
    Response<Person> personResponse;

    try {
      personResponse = apiService.updatePersonCall(person).execute();

      if (personResponse.isSuccessful()) {
        return personResponse.body();
      }

      throw TypeErrorConvert.parseError(personResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.SCHOOL, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public List<Person> getTeachers(String teachers) throws ApiException {
    Response<List<Person>> teachersResponse;

    try {
      teachersResponse = apiService.getTeachersCall(teachers).execute();

      if (teachersResponse.isSuccessful()) {
        return teachersResponse.body();
      }

      throw TypeErrorConvert.parseError(teachersResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.TEACHERS, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public List<Person> getManagers(String managers) throws ApiException {
    Response<List<Person>> managersResponse;

    try {
      managersResponse = apiService.getManagersCall(managers).execute();

      if (managersResponse.isSuccessful()) {
        return managersResponse.body();
      }

      throw TypeErrorConvert.parseError(managersResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.MANAGERS, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
