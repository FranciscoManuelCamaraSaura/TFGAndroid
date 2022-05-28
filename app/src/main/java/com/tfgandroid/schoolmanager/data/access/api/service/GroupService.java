/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 16:33 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: GroupService.java.
 * Last modified: 29/03/21 16:33.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;

public class GroupService {
  private static final String TAG = GroupService.class.getSimpleName();
  private static GroupService instanceService;
  private static ApiService apiService;

  public GroupService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static GroupService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new GroupService(application);
    }

    return instanceService;
  }

  /*public Group getBelongsGroupCall(Integer id) throws ApiException {
    Response<Group> groupResponse;

    try {
      groupResponse = apiService.getBelongsGroupCall(id).execute();

      if (groupResponse.isSuccessful()) {
        return groupResponse.body();
      }

      throw TypeErrorConvert.parseError(groupResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.STUDENT_GROUP, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public List<Group> getGroup(Integer course_id, Character group_words)
      throws ApiException {
    Response<List<Group>> groupResponse;

    try {
      groupResponse = apiService.getGroupCall(course_id, group_words).execute();

      if (groupResponse.isSuccessful()) {
        return groupResponse.body();
      }

      throw TypeErrorConvert.parseError(groupResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.GROUP, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }*/
}
