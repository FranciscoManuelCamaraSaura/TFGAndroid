/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 17/03/21 20:48 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TypeAdminTypeConvert.java.
 * Last modified: 17/03/21 20:48.
 */

package com.tfgandroid.schoolmanager.data.access.database.convert;

import static com.tfgandroid.schoolmanager.data.enums.Admin.ADMINISTRATIVE;
import static com.tfgandroid.schoolmanager.data.enums.Admin.DIRECTOR;
import static com.tfgandroid.schoolmanager.data.enums.Admin.PSYCHOPEDAGOGUE;
import static com.tfgandroid.schoolmanager.data.enums.Admin.SUB_DIRECTOR;

import androidx.room.TypeConverter;
import com.tfgandroid.schoolmanager.data.enums.Admin;

public class AdminTypeConvert {
  @TypeConverter
  public static Admin toAdmin(int admin) {
    if (admin == DIRECTOR.getAdmin()) {
      return DIRECTOR;
    } else if (admin == SUB_DIRECTOR.getAdmin()) {
      return SUB_DIRECTOR;
    } else if (admin == ADMINISTRATIVE.getAdmin()) {
      return ADMINISTRATIVE;
    } else if (admin == PSYCHOPEDAGOGUE.getAdmin()) {
      return PSYCHOPEDAGOGUE;
    } else {
      throw new IllegalArgumentException("Could not recognize admin");
    }
  }

  @TypeConverter
  public static Integer toInteger(Admin admin) {
    return admin.getAdmin();
  }
}
