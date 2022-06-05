/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:15 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TrimesterTypeConvert.java.
 * Last modified: 26/05/21 17:15.
 */

package com.tfgandroid.schoolmanager.data.access.database.convert;

import static com.tfgandroid.schoolmanager.data.enums.Evaluation.FIRST;
import static com.tfgandroid.schoolmanager.data.enums.Evaluation.SECOND;
import static com.tfgandroid.schoolmanager.data.enums.Evaluation.THIRD;

import androidx.room.TypeConverter;
import com.tfgandroid.schoolmanager.data.enums.Evaluation;

public class EvaluationTypeConvert {
  @TypeConverter
  public static Evaluation toEvaluation(int evaluation) {
    if (evaluation == FIRST.getEvaluation()) {
      return FIRST;
    } else if (evaluation == SECOND.getEvaluation()) {
      return SECOND;
    } else if (evaluation == THIRD.getEvaluation()) {
      return THIRD;
    } else {
      throw new IllegalArgumentException("Could not recognize trimester");
    }
  }

  @TypeConverter
  public static Integer toInteger(Evaluation evaluation) {
    return evaluation.getEvaluation();
  }
}
