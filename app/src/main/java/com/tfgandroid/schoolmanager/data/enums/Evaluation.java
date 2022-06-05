/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:12 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: trimester.java.
 * Last modified: 26/05/21 17:12.
 */

package com.tfgandroid.schoolmanager.data.enums;

import com.google.gson.annotations.SerializedName;

public enum Evaluation {
  @SerializedName("first_trimester")
  FIRST(0),
  @SerializedName("second_trimester")
  SECOND(1),
  @SerializedName("third_trimester")
  THIRD(2);

  private final int evaluation;

  Evaluation(int evaluation) {
    this.evaluation = evaluation;
  }

  public int getEvaluation() {
    return evaluation;
  }
}
