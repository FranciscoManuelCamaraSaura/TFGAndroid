/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Subject.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import java.util.Objects;

@Entity(
    tableName = "subject",
    primaryKeys = {"code"},
    indices = @Index("code"))
public class Subject {
  @NonNull private String code;
  private String name;
  private String description;

  public Subject(@NonNull String code, String name, String description) {
    this.code = code;
    this.name = name;
    this.description = description;
  }

  @NonNull
  public String getCode() {
    return code;
  }

  public void setCode(@NonNull String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Subject)) {
      return false;
    }
    Subject subject = (Subject) o;
    return getCode().equals(subject.getCode())
        && getName().equals(subject.getName())
        && getDescription().equals(subject.getDescription());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCode(), getName(), getDescription());
  }

  @NonNull
  @Override
  public String toString() {
    return "Subject{"
        + "code='"
        + code
        + '\''
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
