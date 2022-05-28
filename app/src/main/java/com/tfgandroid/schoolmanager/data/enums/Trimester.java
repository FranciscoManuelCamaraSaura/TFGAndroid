/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:12 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: trimester.java.
 * Last modified: 26/05/21 17:12.
 */

package com.tfgandroid.schoolmanager.data.enums;

import com.google.gson.annotations.SerializedName;

public enum Trimester {
  @SerializedName("first")
  FIRST(0),
  @SerializedName("second")
  SECOND(1),
  @SerializedName("third")
  THIRD(2);

  private final int trimester;

  Trimester(int trimester) {
    this.trimester = trimester;
  }

  public int getTrimester() {
    return trimester;
  }
}
