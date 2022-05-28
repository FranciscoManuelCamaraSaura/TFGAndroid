/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 16:27 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: CourseService.java.
 * Last modified: 29/03/21 16:27.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import java.io.IOException;
import retrofit2.Response;

public class CourseService {
  private static final String TAG = CourseService.class.getSimpleName();
  private static CourseService instanceService;
  private static ApiService apiService;

  public CourseService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static CourseService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new CourseService(application);
    }

    return instanceService;
  }

  /*public List<Course> getCoursesCall(Integer school) throws ApiException {
    Response<List<Course>> coursesResponse;

    try {
      coursesResponse = apiService.getCoursesCall(school).execute();

      if (coursesResponse.isSuccessful()) {
        return coursesResponse.body();
      }

      throw TypeErrorConvert.parseError(coursesResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.COURSES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }*/

  public Course getCourse(int course_id) throws ApiException {
      Response<Course> courseResponse;

      try {
          courseResponse = apiService.getCourseCall(course_id).execute();

          if (courseResponse.isSuccessful()) {
              return courseResponse.body();
          }

          throw TypeErrorConvert.parseError(courseResponse);
      } catch (IOException e) {
          ApiException apiException = new ApiException(TypeError.COURSES, e.getMessage());

          Log.e(TAG, apiException.getMessage(), e);

          throw apiException;
      }
  }
}
