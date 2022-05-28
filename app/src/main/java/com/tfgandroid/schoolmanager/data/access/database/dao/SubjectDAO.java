/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 9/10/20 11:49 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Subject.java.
 * Last modified: 9/10/20 11:49.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import java.util.List;

@Dao
public interface SubjectDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Subject subject);

  @Update
  void update(Subject subject);

  @Delete
  void delete(Subject subject);

  @Query("SELECT * FROM subject WHERE code = :code_id")
  Subject getSubject(String code_id);

  @Query("SELECT * FROM subject")
  List<Subject> getSubjects();
}
