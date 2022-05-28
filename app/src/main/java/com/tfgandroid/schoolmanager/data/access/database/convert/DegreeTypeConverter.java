/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 17/03/21 20:15 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DegreeConverter.java.
 * Last modified: 17/03/21 20:15.
 */

package com.tfgandroid.schoolmanager.data.access.database.convert;

import static com.tfgandroid.schoolmanager.data.enums.Degree.BACHELOR;
import static com.tfgandroid.schoolmanager.data.enums.Degree.HIGH_SCHOOL;
import static com.tfgandroid.schoolmanager.data.enums.Degree.PRESCHOOL;
import static com.tfgandroid.schoolmanager.data.enums.Degree.PRIMARY;

import androidx.room.TypeConverter;
import com.tfgandroid.schoolmanager.data.enums.Degree;

public class DegreeTypeConverter {
  @TypeConverter
  public static Degree toDegree(int degree) {
    if (degree == PRESCHOOL.getDegree()) {
      return PRESCHOOL;
    } else if (degree == PRIMARY.getDegree()) {
      return PRIMARY;
    } else if (degree == HIGH_SCHOOL.getDegree()) {
      return HIGH_SCHOOL;
    } else if (degree == BACHELOR.getDegree()) {
      return BACHELOR;
    } else {
      throw new IllegalArgumentException("Could not recognize degree");
    }
  }

  @TypeConverter
  public static Integer toInteger(Degree degree) {
    return degree.getDegree();
  }
}
