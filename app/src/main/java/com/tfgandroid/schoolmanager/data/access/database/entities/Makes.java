/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Makes.java.
 * Last modified: 3/03/21 17:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import java.util.Objects;

@Entity(
    tableName = "makes",
    primaryKeys = {"student", "exam"},
    foreignKeys = {
      @ForeignKey(
          entity = Student.class,
          parentColumns = "id",
          childColumns = "student",
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = Exam.class,
          parentColumns = {"id"},
          childColumns = {"exam"},
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index({"student"}), @Index("exam")})
public class Makes {
  private int student;
  private int exam;
  private double note;

  public Makes(int student, int exam, double note) {
    this.student = student;
    this.exam = exam;
    this.note = note;
  }

  public int getStudent() {
    return student;
  }

  public void setStudent(int student) {
    this.student = student;
  }

  public int getExam() {
    return exam;
  }

  public void setExam(int exam) {
    this.exam = exam;
  }

  public double getNote() {
    return note;
  }

  public void setNote(double note) {
    this.note = note;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Makes)) {
      return false;
    }

    Makes makes = (Makes) o;

    return getStudent() == makes.getStudent()
        && getExam() == makes.getExam()
        && Double.compare(makes.getNote(), getNote()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStudent(), getExam(), getNote());
  }

  @NonNull
  @Override
  public String toString() {
    return "Makes {" + "student = " + student + ", exam = " + exam + ", note = " + note + '}';
  }
}
