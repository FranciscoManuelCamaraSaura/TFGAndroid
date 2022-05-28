/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 17/03/21 20:38 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DataTypeConvert.java.
 * Last modified: 17/03/21 20:38.
 */

package com.tfgandroid.schoolmanager.data.access.database.convert;

import androidx.room.TypeConverter;
import java.sql.Date;

public class DataTypeConvert {
  @TypeConverter
  public static Date toDate(Long dateLong) {
    return dateLong == null ? null : new Date(dateLong);
  }

  @TypeConverter
  public static Long fromDate(Date date) {
    return date == null ? null : date.getTime();
  }
}
