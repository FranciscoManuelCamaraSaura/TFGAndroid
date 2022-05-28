/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/03/21 18:21 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: LegalGuardianDAO.java.
 * Last modified: 1/03/21 18:21.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;

@Dao
public interface LegalGuardianDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(LegalGuardian legalGuardian);

  @Update
  void update(LegalGuardian legalGuardian);

  @Delete
  void delete(LegalGuardian legalGuardian);

  @Query("SELECT * FROM legal_guardian WHERE person = :legal_guardian_dni")
  LegalGuardian getLegalGuardian(String legal_guardian_dni);
}
