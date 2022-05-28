/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 10/09/20 20:36 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Degree.java.
 * Last modified: 25/03/19 16:17.
 */

package com.tfgandroid.schoolmanager.data.enums;

import com.google.gson.annotations.SerializedName;

public enum Degree {
  @SerializedName("preschool")
  PRESCHOOL(0),
  @SerializedName("primary")
  PRIMARY(1),
  @SerializedName("high_school")
  HIGH_SCHOOL(2),
  @SerializedName("bachelor")
  BACHELOR(2);

  private final int degree;

  Degree(int degree) {
    this.degree = degree;
  }

  public int getDegree() {
    return degree;
  }
}
