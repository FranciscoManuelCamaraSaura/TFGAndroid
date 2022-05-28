/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/03/21 20:42 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Login.java.
 * Last modified: 22/03/21 20:42.
 */

package com.tfgandroid.schoolmanager.data.access.api.entities;

import com.google.gson.GsonBuilder;

public class Login {
  private String user_name;
  private String password;
  private int school_id;

  public Login(String user_name, String password, int school_id) {
    this.user_name = user_name;
    this.password = password;
    this.school_id = school_id;
  }

  public String getUserName() {
    return user_name;
  }

  public void setUserName(String user_name) {
    this.user_name = user_name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getIdSchool() {
    return school_id;
  }

  public void setIdSchool(int school_id) {
    this.school_id = school_id;
  }

  public String toJson() {
    return new GsonBuilder().create().toJson(this);
  }
}
