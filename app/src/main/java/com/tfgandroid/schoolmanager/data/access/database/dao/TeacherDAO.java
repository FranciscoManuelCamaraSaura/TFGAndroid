/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/03/21 18:18 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TeacherDAO.java.
 * Last modified: 1/03/21 18:18.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Teacher;
import java.util.List;

@Dao
public interface TeacherDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Teacher teacher);

  @Update
  void update(Teacher teacher);

  @Delete
  void delete(Teacher teacher);

  @Query("SELECT * FROM teacher WHERE person = :teacher_dni")
  Teacher getTeacher(String teacher_dni);

  @Query("SELECT * FROM teacher")
  List<Teacher> getTeachers();
}
