/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 10/09/20 20:35 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TypeAdmin.java.
 * Last modified: 25/03/19 16:17.
 */

package com.tfgandroid.schoolmanager.data.enums;

import com.google.gson.annotations.SerializedName;

public enum Admin {
  @SerializedName("director")
  DIRECTOR(0),
  @SerializedName("subdirector")
  SUB_DIRECTOR(1),
  @SerializedName("administrative")
  ADMINISTRATIVE(2),
  @SerializedName("psychopedagogue")
  PSYCHOPEDAGOGUE(3);

  private final int admin;

  Admin(int admin) {
    this.admin = admin;
  }

  public int getAdmin() {
    return admin;
  }
}
