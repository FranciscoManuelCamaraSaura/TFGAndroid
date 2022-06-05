/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/03/21 18:26 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MakeDAO.java.
 * Last modified: 1/03/21 18:26.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Makes;
import java.util.List;

@Dao
public interface MakesDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Makes makes);

  @Update
  void update(Makes makes);

  @Delete
  void delete(Makes makes);

  @Query("SELECT exam FROM makes WHERE student = :student_id")
  List<Integer> getExam(Integer student_id);

  @Query("SELECT note FROM makes WHERE exam = :exam_id")
  Double getNote(Integer exam_id);
}
