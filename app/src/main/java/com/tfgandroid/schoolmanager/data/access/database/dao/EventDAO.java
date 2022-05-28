/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 9/10/20 11:54 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventDAO.java.
 * Last modified: 9/10/20 11:54.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;

@Dao
public interface EventDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Event event);

  @Update
  void update(Event event);

  @Delete
  void delete(Event event);

  @Query("SELECT * FROM event WHERE id = :event_id AND school = :school_id")
  Event getEvent(int event_id, int school_id);
}
