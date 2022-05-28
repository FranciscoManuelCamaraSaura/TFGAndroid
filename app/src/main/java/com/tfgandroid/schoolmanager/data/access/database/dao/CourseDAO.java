/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 15/09/20 21:30 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Course.java.
 * Last modified: 15/09/20 21:30.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import java.util.List;

@Dao
public interface CourseDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Course course);

  @Update
  void update(Course course);

  @Delete
  void delete(Course course);

  @Query("SELECT * FROM course WHERE school = :school_id")
  List<Course> getCourses(int school_id);

  @Query("SELECT * FROM course WHERE id = :course_id")
  Course getCourseById(int course_id);

  @Query("SELECT * FROM course WHERE number = :courseNumber AND degree = :courseDegree")
  Course getCourse(int courseNumber, int courseDegree);
}
