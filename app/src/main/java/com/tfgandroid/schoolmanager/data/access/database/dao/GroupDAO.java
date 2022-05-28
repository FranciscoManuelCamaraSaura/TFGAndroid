/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 9/10/20 11:08 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Group.java.
 * Last modified: 9/10/20 11:08.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;

@Dao
public interface GroupDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Group group);

  @Update
  void update(Group group);

  @Delete
  void delete(Group group);

  @Query(
      "SELECT * FROM group_course WHERE course_id = :course_id AND group_words = :group_words")
  Group getGroup(int course_id, String group_words);
}
