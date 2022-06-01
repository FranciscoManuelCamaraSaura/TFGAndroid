/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 18:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertDAO.java.
 * Last modified: 29/5/22 13:17.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import java.util.List;

@Dao
public interface AlertDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Alert alert);

  @Update
  void update(Alert alert);

  @Delete
  void delete(Alert alert);

  @Query("SELECT * FROM alert WHERE id = :alert_id")
  Alert getAlert(int alert_id);

  @Query("SELECT * FROM alert WHERE receiver = :receiver_dni")
  List<Alert> getAlertsReceived(String receiver_dni);
}
