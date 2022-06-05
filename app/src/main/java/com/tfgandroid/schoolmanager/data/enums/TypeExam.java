/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 10/09/20 20:35 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TypeExam.java.
 * Last modified: 25/03/19 16:16.
 */

package com.tfgandroid.schoolmanager.data.enums;

import com.google.gson.annotations.SerializedName;

public enum TypeExam {
  @SerializedName("written")
  WRITTEN(0),
  @SerializedName("oral")
  ORAL(1),
  @SerializedName("presentation")
  PRESENTATION(2),
  @SerializedName("exhibition")
  EXHIBITION(3),
  @SerializedName("optional_work")
  OPTIONAL_WORK(4),
  @SerializedName("homework")
  HOMEWORK(5);

  private final int type_exam;

  TypeExam(int type_exam) {
    this.type_exam = type_exam;
  }

  public int getType_exam() {
    return type_exam;
  }
}
