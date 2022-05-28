/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:15 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TrimesterTypeConvert.java.
 * Last modified: 26/05/21 17:15.
 */

package com.tfgandroid.schoolmanager.data.access.database.convert;

import static com.tfgandroid.schoolmanager.data.enums.Trimester.FIRST;
import static com.tfgandroid.schoolmanager.data.enums.Trimester.SECOND;
import static com.tfgandroid.schoolmanager.data.enums.Trimester.THIRD;

import androidx.room.TypeConverter;
import com.tfgandroid.schoolmanager.data.enums.Trimester;

public class TrimesterTypeConvert {
  @TypeConverter
  public static Trimester toTrimester(int trimester) {
    if (trimester == FIRST.getTrimester()) {
      return FIRST;
    } else if (trimester == SECOND.getTrimester()) {
      return SECOND;
    } else if (trimester == THIRD.getTrimester()) {
      return THIRD;
    } else {
      throw new IllegalArgumentException("Could not recognize trimester");
    }
  }

  @TypeConverter
  public static Integer toInteger(Trimester trimester) {
    return trimester.getTrimester();
  }
}
