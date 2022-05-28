/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 28/8/21 17:54 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Array.java.
 * Last modified: 28/8/21 17:54.
 */

package com.tfgandroid.schoolmanager.data.access.api.entities;

import com.google.gson.GsonBuilder;

public class Array {
  private String[] elements;

  public Array(String[] elements) {
    this.elements = elements;
  }

  public String[] getElements() {
    return elements;
  }

  public void setElements(String[] elements) {
    this.elements = elements;
  }

  public String toJson() {
    return new GsonBuilder().create().toJson(this);
  }
}
