/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: GroupHavePreceptor.java.
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
    tableName = "group_have_preceptor",
    primaryKeys = {"course_id", "group_words", "preceptor"},
    foreignKeys = {
      @ForeignKey(
          entity = Group.class,
          parentColumns = {"course_id", "group_words"},
          childColumns = {"course_id", "group_words"},
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = Teacher.class,
          parentColumns = {"person"},
          childColumns = {"preceptor"},
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index({"course_id", "group_words"}), @Index("preceptor")})
public class GroupHavePreceptor {
  private int course_id;
  @NonNull private String group_words;
  private @NonNull String preceptor;

  public GroupHavePreceptor(int course_id, @NonNull String group_words, @NonNull String preceptor) {
    this.course_id = course_id;
    this.group_words = group_words;
    this.preceptor = preceptor;
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
  public String getPreceptor() {
    return preceptor;
  }

  public void setPreceptor(@NonNull String preceptor) {
    this.preceptor = preceptor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GroupHavePreceptor)) {
      return false;
    }
    GroupHavePreceptor that = (GroupHavePreceptor) o;
    return getCourse_id() == that.getCourse_id()
        && getGroup_words().equals(that.getGroup_words())
        && getPreceptor().equals(that.getPreceptor());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCourse_id(), getGroup_words(), getPreceptor());
  }

  @NonNull
  @Override
  public String toString() {
    return "GroupHavePreceptor{"
        + "course_id="
        + course_id
        + ", group_words='"
        + group_words
        + '\''
        + ", preceptor='"
        + preceptor
        + '\''
        + '}';
  }
}
