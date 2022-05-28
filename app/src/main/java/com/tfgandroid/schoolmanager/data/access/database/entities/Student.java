/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Student.java.
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
import com.tfgandroid.schoolmanager.data.access.database.convert.DataTypeConvert;
import java.sql.Date;
import java.util.Objects;

@Entity(
    tableName = "student",
    foreignKeys = {
      @ForeignKey(
          entity = LegalGuardian.class,
          parentColumns = "person",
          childColumns = "legal_guardian",
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = Group.class,
          parentColumns = {"course_id", "group_words"},
          childColumns = {"course_id", "group_words"},
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index("id"), @Index("legal_guardian"), @Index({"course_id", "group_words"})})
public class Student {
  @PrimaryKey private int id;
  private String name;
  private String surnames;
  private int phone;

  @TypeConverters(DataTypeConvert.class)
  @NonNull
  private Date birthday;

  private String legal_guardian;
  private int course_id;
  @NonNull private String group_words;

  public Student(
      int id,
      String name,
      String surnames,
      int phone,
      @NonNull Date birthday,
      String legal_guardian,
      int course_id,
      @NonNull String group_words) {
    this.id = id;
    this.name = name;
    this.surnames = surnames;
    this.phone = phone;
    this.birthday = birthday;
    this.legal_guardian = legal_guardian;
    this.course_id = course_id;
    this.group_words = group_words;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurnames() {
    return surnames;
  }

  public void setSurnames(String surnames) {
    this.surnames = surnames;
  }

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    this.phone = phone;
  }

  public @NonNull Date getBirthday() {
    return birthday;
  }

  public void setBirthday(@NonNull Date birthday) {
    this.birthday = birthday;
  }

  public String getLegal_guardian() {
    return legal_guardian;
  }

  public void setLegal_guardian(String legal_guardian) {
    this.legal_guardian = legal_guardian;
  }

  public int getCourse_id() {
    return course_id;
  }

  public void setCourse_id(int course_id) {
    this.course_id = course_id;
  }

  @NonNull
  public String getGroup_words() {
    return group_words;
  }

  public void setGroup_words(@NonNull String group_words) {
    this.group_words = group_words;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Student)) {
      return false;
    }

    Student student = (Student) o;

    return getId() == student.getId()
        && getPhone() == student.getPhone()
        && getCourse_id() == student.getCourse_id()
        && getName().equals(student.getName())
        && getSurnames().equals(student.getSurnames())
        && getBirthday().equals(student.getBirthday())
        && getLegal_guardian().equals(student.getLegal_guardian())
        && getGroup_words().equals(student.getGroup_words());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getName(),
        getSurnames(),
        getPhone(),
        getBirthday(),
        getLegal_guardian(),
        getCourse_id(),
        getGroup_words());
  }

  @NonNull
  @Override
  public String toString() {
    return "Student{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", surnames='"
        + surnames
        + '\''
        + ", phone="
        + phone
        + ", birthday="
        + birthday
        + ", legal_guardian='"
        + legal_guardian
        + '\''
        + ", course_id="
        + course_id
        + ", group_words='"
        + group_words
        + '\''
        + '}';
  }
}
