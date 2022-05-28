/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/03/21 18:07 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: StudentDAO.java.
 * Last modified: 1/03/21 18:07.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;

@Dao
public interface StudentDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Student student);

  @Update
  void update(Student student);

  @Delete
  void delete(Student student);

  @Query("SELECT * FROM student WHERE legal_guardian = :legalGuardian_id")
  Student getStudent(String legalGuardian_id);

  @Query("SELECT * FROM student WHERE id = :student_id")
  Student getStudentById(int student_id);

  @Query("SELECT course_id, group_words FROM student WHERE id = :student_id")
  Group getGroup(int student_id);
}
