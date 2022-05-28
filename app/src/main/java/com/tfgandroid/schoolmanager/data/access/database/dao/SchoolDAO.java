/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 21/09/20 19:36 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: School.java.
 * Last modified: 21/09/20 19:36.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import java.util.List;

@Dao
public interface SchoolDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(School school);

  @Update
  void update(School school);

  @Delete
  void delete(School school);

  @Query("SELECT * FROM school WHERE id = :id")
  School getSchool(int id);

  @Query("SELECT * FROM school")
  List<School> getAllSchools();
}
