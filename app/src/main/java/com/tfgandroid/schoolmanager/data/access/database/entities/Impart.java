/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Impart.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import java.util.Objects;

@Entity(
    tableName = "impart",
    primaryKeys = {"course_id", "group_words", "subject"},
    foreignKeys = {
      @ForeignKey(
          entity = Group.class,
          parentColumns = {"course_id", "group_words"},
          childColumns = {"course_id", "group_words"},
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = Subject.class,
          parentColumns = {"code"},
          childColumns = {"subject"},
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = Teacher.class,
          parentColumns = {"person"},
          childColumns = {"teacher"},
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index({"course_id", "group_words"}), @Index("subject"), @Index("teacher")})
public class Impart {
  private int course_id;
  @NonNull private String group_words;
  @NonNull private String subject;
  @NonNull private String teacher;

  public Impart(
      int course_id,
      @NonNull String group_words,
      @NonNull String subject,
      @NonNull String teacher) {
    this.course_id = course_id;
    this.group_words = group_words;
    this.subject = subject;
    this.teacher = teacher;
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

  @NonNull
  public String getSubject() {
    return subject;
  }

  public void setSubject(@NonNull String subject) {
    this.subject = subject;
  }

  @NonNull
  public String getTeacher() {
    return teacher;
  }

  public void setTeacher(@NonNull String teacher) {
    this.teacher = teacher;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Impart)) {
      return false;
    }

    Impart impart = (Impart) o;

    return getCourse_id() == impart.getCourse_id()
        && getGroup_words().equals(impart.getGroup_words())
        && getSubject().equals(impart.getSubject())
        && getTeacher().equals(impart.getTeacher());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCourse_id(), getGroup_words(), getSubject(), getTeacher());
  }

  @NonNull
  @Override
  public String toString() {
    return "Impart{"
        + "course_id="
        + course_id
        + ", group_words='"
        + group_words
        + '\''
        + ", subject='"
        + subject
        + '\''
        + ", teacher='"
        + teacher
        + '\''
        + '}';
  }
}
