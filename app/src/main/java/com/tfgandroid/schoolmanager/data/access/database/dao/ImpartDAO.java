/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/03/21 18:31 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ImpartDAO.java.
 * Last modified: 1/03/21 18:31.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Impart;
import java.util.List;

@Dao
public interface ImpartDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Impart impart);

  @Update
  void update(Impart impart);

  @Delete
  void delete(Impart impart);

  @Query("SELECT * FROM impart WHERE course_id = :course_id AND group_words = :group_words")
  List<Impart> getImpart(int course_id, String group_words);

  @Query("SELECT subject FROM impart WHERE course_id = :course_id AND group_words = :group_words")
  List<String> getSubject(int course_id, String group_words);

  @Query("SELECT subject FROM impart WHERE teacher = :teacher")
  List<String> getSubjects(String teacher);

  @Query("SELECT teacher FROM impart WHERE subject = :subject_id")
  String getTeacher(String subject_id);

  @Query(
      "SELECT teacher FROM impart WHERE course_id = :course_id AND group_words = :group_words AND subject = :subject_id")
  String getTeacher(int course_id, String group_words, String subject_id);

  @Query("SELECT teacher FROM impart WHERE course_id = :course_id AND group_words = :group_words")
  List<String> getTeacher(int course_id, String group_words);
}
