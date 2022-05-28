/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 19:34 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TypeErrorConvert.java.
 * Last modified: 29/03/21 19:34.
 */

package com.tfgandroid.schoolmanager.data.access.api.error;

import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class TypeErrorConvert {
  public static ApiException parseError(Response<?> response) {
    Converter<ResponseBody, ApiException> converter =
        RetrofitClient.getRetrofitClient()
            .responseBodyConverter(ApiException.class, new Annotation[0]);

    try {
      if (response.errorBody() != null) {
        return converter.convert(response.errorBody());
      }

      return new ApiException(TypeError.OTHER);
    } catch (IOException e) {
      return new ApiException(TypeError.OTHER);
    }
  }
}
