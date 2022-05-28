/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/03/21 17:14 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ExamDAO.java.
 * Last modified: 1/03/21 17:14.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;

@Dao
public interface ExamDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Exam exam);

  @Update
  void update(Exam exam);

  @Delete
  void delete(Exam exam);

  @Query("SELECT * FROM exam WHERE id = :exam_id")
  Exam getExam(int exam_id);
}
