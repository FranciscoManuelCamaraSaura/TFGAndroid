/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 21/09/20 19:48 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Manager.java.
 * Last modified: 21/09/20 19:48.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.enums.Admin;
import java.util.List;

@Dao
public interface ManagerDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Manager manager);

  @Update
  void update(Manager manager);

  @Delete
  void delete(Manager manager);

  @Query("SELECT * FROM manager WHERE person = :manager_dni")
  Manager getManager(String manager_dni);

  @Query("SELECT * FROM manager WHERE school = :school_id AND person = :manager_dni")
  Manager getManager(int school_id, String manager_dni);

  @Query("SELECT * FROM manager")
  List<Manager> getManagers();

  @Query("SELECT * FROM manager WHERE type_admin = :type_admin")
  List<Manager> getManagersByType(Admin type_admin);

  @Query("SELECT * FROM manager WHERE school = :school_id")
  List<Manager> getManagersBySchool(int school_id);
}
