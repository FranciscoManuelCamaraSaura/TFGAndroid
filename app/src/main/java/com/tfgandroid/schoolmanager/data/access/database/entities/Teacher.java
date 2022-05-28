/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Teacher.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Objects;

@Entity(
    tableName = "teacher",
    foreignKeys =
        @ForeignKey(
            entity = Person.class,
            parentColumns = "dni",
            childColumns = "person",
            onDelete = CASCADE,
            onUpdate = CASCADE))
public class Teacher {
  @PrimaryKey @NonNull private String person;
  private boolean preceptor;

  public Teacher(@NonNull String person, boolean preceptor) {
    this.person = person;
    this.preceptor = preceptor;
  }

  @NonNull
  public String getPerson() {
    return person;
  }

  public void setPerson(@NonNull String person) {
    this.person = person;
  }

  public boolean isPreceptor() {
    return preceptor;
  }

  public void setPreceptor(boolean preceptor) {
    this.preceptor = preceptor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Teacher)) {
      return false;
    }

    Teacher teacher = (Teacher) o;

    return getPerson().equals(teacher.getPerson()) && isPreceptor() == teacher.isPreceptor();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPerson(), isPreceptor());
  }

  @NonNull
  @Override
  public String toString() {
    return "Teacher{" + "person='" + person + '\'' + ", preceptor=" + preceptor + '}';
  }
}
