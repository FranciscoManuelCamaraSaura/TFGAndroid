/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 19:30 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ApiException.java.
 * Last modified: 29/03/21 19:30.
 */

package com.tfgandroid.schoolmanager.data.access.api.service.exceptions;

import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;

public class ApiException extends Throwable {
  private final TypeError type;
  private final String message;

  public ApiException(TypeError type) {
    this.type = type;
    this.message = null;
  }

  public ApiException(TypeError type, String message) {
    this.type = type;
    this.message = message;
  }

  public TypeError getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }
}
