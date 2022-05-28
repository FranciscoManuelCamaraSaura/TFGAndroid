/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/03/21 18:30 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TypeExamTypeConvert.java.
 * Last modified: 22/03/21 18:30.
 */

package com.tfgandroid.schoolmanager.data.access.database.convert;

import static com.tfgandroid.schoolmanager.data.enums.TypeExam.EXHIBITION;
import static com.tfgandroid.schoolmanager.data.enums.TypeExam.HOMEWORK;
import static com.tfgandroid.schoolmanager.data.enums.TypeExam.OPTIONAL_WORK;
import static com.tfgandroid.schoolmanager.data.enums.TypeExam.ORAL;
import static com.tfgandroid.schoolmanager.data.enums.TypeExam.PRESENTATION;
import static com.tfgandroid.schoolmanager.data.enums.TypeExam.WRITTEN;

import androidx.room.TypeConverter;
import com.tfgandroid.schoolmanager.data.enums.TypeExam;

public class TypeExamTypeConvert {
  @TypeConverter
  public static TypeExam toExam(int typeExam) {
    if (typeExam == WRITTEN.getExam()) {
      return WRITTEN;
    } else if (typeExam == ORAL.getExam()) {
      return ORAL;
    } else if (typeExam == PRESENTATION.getExam()) {
      return PRESENTATION;
    } else if (typeExam == EXHIBITION.getExam()) {
      return EXHIBITION;
    } else if (typeExam == OPTIONAL_WORK.getExam()) {
      return OPTIONAL_WORK;
    } else if (typeExam == HOMEWORK.getExam()) {
      return HOMEWORK;
    } else {
      throw new IllegalArgumentException("Could not recognize typeExam");
    }
  }

  @TypeConverter
  public static Integer toInteger(TypeExam exam) {
    return exam.getExam();
  }
}
