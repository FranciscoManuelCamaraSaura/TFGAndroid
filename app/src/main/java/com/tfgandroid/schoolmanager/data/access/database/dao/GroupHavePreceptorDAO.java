/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/03/21 17:38 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: GroupHavePreceptorDAO.java.
 * Last modified: 1/03/21 17:38.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.GroupHavePreceptor;

@Dao
public interface GroupHavePreceptorDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(GroupHavePreceptor groupHavePreceptor);

  @Update
  void update(GroupHavePreceptor groupHavePreceptor);

  @Delete
  void delete(GroupHavePreceptor groupHavePreceptor);

  @Query(
      "SELECT preceptor FROM group_have_preceptor WHERE  course_id = :course_id AND group_words = :group_words")
  String getGroupHavePreceptor(int course_id, String group_words);
}
