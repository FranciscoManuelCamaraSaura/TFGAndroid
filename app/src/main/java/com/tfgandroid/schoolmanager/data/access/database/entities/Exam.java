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
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.tfgandroid.schoolmanager.data.access.database.convert.EvaluationTypeConvert;
import com.tfgandroid.schoolmanager.data.access.database.convert.TypeExamTypeConvert;
import com.tfgandroid.schoolmanager.data.enums.Evaluation;
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
    indices = {
      @Index(
          value = {"course_id", "group_words", "subject", "event"},
          unique = true),
      @Index({"course_id", "group_words"}),
      @Index("subject"),
      @Index("event")
    })
public class Exam {
  @PrimaryKey private int id;
  private int course_id;
  @NonNull private String group_words;
  private int event;
  private String subject;

  @TypeConverters(TypeExamTypeConvert.class)
  @NonNull
  private TypeExam type_exam;

  @TypeConverters(EvaluationTypeConvert.class)
  @NonNull
  private Evaluation evaluation;

  public Exam(
      int id,
      int course_id,
      @NonNull String group_words,
      int event,
      String subject,
      @NonNull TypeExam type_exam,
      @NonNull Evaluation evaluation) {
    this.id = id;
    this.course_id = course_id;
    this.group_words = group_words;
    this.event = event;
    this.subject = subject;
    this.type_exam = type_exam;
    this.evaluation = evaluation;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getEvent() {
    return event;
  }

  public void setEvent(int event) {
    this.event = event;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @NonNull
  public TypeExam getType_exam() {
    return type_exam;
  }

  public void setType_exam(@NonNull TypeExam type_exam) {
    this.type_exam = type_exam;
  }

  @NonNull
  public Evaluation getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(@NonNull Evaluation evaluation) {
    this.evaluation = evaluation;
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
        && getCourse_id() == exam.getCourse_id()
        && getEvent() == exam.getEvent()
        && getGroup_words().equals(exam.getGroup_words())
        && getSubject().equals(exam.getSubject())
        && getType_exam() == exam.getType_exam()
        && getEvaluation() == exam.getEvaluation();
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getCourse_id(),
        getGroup_words(),
        getEvent(),
        getSubject(),
        getType_exam(),
        getEvaluation());
  }

  @NonNull
  @Override
  public String toString() {
    return "Exam{"
        + "id="
        + id
        + ", course_id="
        + course_id
        + ", group_words='"
        + group_words
        + '\''
        + ", event="
        + event
        + ", subject='"
        + subject
        + '\''
        + ", typeExam="
        + type_exam
        + ", evaluation="
        + evaluation
        + '}';
  }
}
