/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Course.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.tfgandroid.schoolmanager.data.access.database.convert.DegreeTypeConverter;
import com.tfgandroid.schoolmanager.data.enums.Degree;
import java.util.Objects;

@Entity(
    tableName = "course",
    foreignKeys =
        @ForeignKey(
            entity = School.class,
            parentColumns = "id",
            childColumns = "school",
            onDelete = CASCADE,
            onUpdate = CASCADE),
    indices =
        @Index(
            value = {"school", "degree", "number"},
            unique = true))
public class Course {
  @PrimaryKey private int id;
  private int school;
  private int number;

  @TypeConverters(DegreeTypeConverter.class)
  @NonNull
  private Degree degree;

  public Course(int id, int school, int number, @NonNull Degree degree) {
    this.id = id;
    this.school = school;
    this.number = number;
    this.degree = degree;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSchool() {
    return school;
  }

  public void setSchool(int school) {
    this.school = school;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  @NonNull
  public Degree getDegree() {
    return degree;
  }

  public void setDegree(@NonNull Degree degree) {
    this.degree = degree;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Course)) {
      return false;
    }

    Course course = (Course) o;

    return getId() == getId()
        && getSchool() == course.getSchool()
        && getNumber() == course.getNumber()
        && getDegree() == course.getDegree();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getSchool(), getNumber(), getDegree());
  }

  @NonNull
  @Override
  public String toString() {
    return "Course{"
        + "id="
        + id
        + ", school="
        + school
        + ", number="
        + number
        + ", degree="
        + degree
        + '}';
  }
}
