/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 21/09/20 19:44 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Person.java.
 * Last modified: 21/09/20 19:44.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;

@Dao
public interface PersonDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Person person);

  @Update
  void update(Person person);

  @Delete
  void delete(Person person);

  @Query("SELECT * FROM person WHERE dni = :dni")
  Person getPerson(String dni);
}
