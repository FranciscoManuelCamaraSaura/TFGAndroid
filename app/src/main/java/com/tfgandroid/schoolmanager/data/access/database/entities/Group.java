/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Group.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import java.util.Objects;

@Entity(
    tableName = "group_course",
    primaryKeys = {"course_id", "group_words"},
    foreignKeys =
        @ForeignKey(
            entity = Course.class,
            parentColumns = {"id"},
            childColumns = {"course_id"},
            onDelete = CASCADE,
            onUpdate = CASCADE))
public class Group {
  private int course_id;
  @NonNull private String group_words;

  public Group(int course_id, @NonNull String group_words) {
    this.course_id = course_id;
    this.group_words = group_words;
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

    if (!(o instanceof Group)) {
      return false;
    }

    Group group = (Group) o;

    return getCourse_id() == group.getCourse_id()
        && getGroup_words().equals(group.getGroup_words());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCourse_id(), getGroup_words());
  }

  @NonNull
  @Override
  public String toString() {
    return "Group{" + "course_id=" + course_id + ", group_words='" + group_words + '\'' + '}';
  }
}
