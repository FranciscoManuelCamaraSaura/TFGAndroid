/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Exam.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.tfgandroid.schoolmanager.data.access.database.convert.TrimesterTypeConvert;
import com.tfgandroid.schoolmanager.data.access.database.convert.TypeExamTypeConvert;
import com.tfgandroid.schoolmanager.data.enums.Trimester;
import com.tfgandroid.schoolmanager.data.enums.TypeExam;
import java.util.Objects;

@Entity(
    tableName = "exam",
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
          entity = Event.class,
          parentColumns = {"id"},
          childColumns = {"event"},
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index({"course_id", "group_words"}), @Index("subject"), @Index("event")})
public class Exam {
  @PrimaryKey private int id;
  @Embedded @NonNull private Group groupMakeExam;
  private int event;
  private String subject;

  @TypeConverters(TypeExamTypeConvert.class)
  @NonNull
  private TypeExam typeExam;

  @TypeConverters(TrimesterTypeConvert.class)
  @NonNull
  private Trimester trimester;

  public Exam(
      int id,
      @NonNull Group groupMakeExam,
      int event,
      @NonNull String subject,
      @NonNull TypeExam typeExam,
      @NonNull Trimester trimester) {
    this.id = id;
    this.groupMakeExam = groupMakeExam;
    this.event = event;
    this.subject = subject;
    this.typeExam = typeExam;
    this.trimester = trimester;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public @NonNull Group getGroupMakeExam() {
    return groupMakeExam;
  }

  public void setGroupMakeExam(@NonNull Group groupMakeExam) {
    this.groupMakeExam = groupMakeExam;
  }

  public int getEvent() {
    return event;
  }

  public void setEvent(int event) {
    this.event = event;
  }

  public @NonNull String getSubject() {
    return subject;
  }

  public void setSubject(@NonNull String subject) {
    this.subject = subject;
  }

  @NonNull
  public TypeExam getTypeExam() {
    return typeExam;
  }

  public void setTypeExam(@NonNull TypeExam typeExam) {
    this.typeExam = typeExam;
  }

  @NonNull
  public Trimester getTrimester() {
    return trimester;
  }

  public void setTrimester(@NonNull Trimester trimester) {
    this.trimester = trimester;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Exam)) {
      return false;
    }
    Exam exam = (Exam) o;
    return getId() == exam.getId()
        && getEvent() == exam.getEvent()
        && getGroupMakeExam().equals(exam.getGroupMakeExam())
        && getSubject().equals(exam.getSubject())
        && getTypeExam() == exam.getTypeExam()
        && getTrimester() == exam.getTrimester();
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(), getGroupMakeExam(), getEvent(), getSubject(), getTypeExam(), getTrimester());
  }

  @NonNull
  @Override
  public String toString() {
    return "Exam{"
        + "id="
        + id
        + ", groupMakeExam="
        + groupMakeExam
        + ", event="
        + event
        + ", subject='"
        + subject
        + '\''
        + ", typeExam="
        + typeExam
        + ", trimester="
        + trimester
        + '}';
  }
}
