/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 20:12 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: LegalGuardianRepository.java.
 * Last modified: 3/03/21 20:12.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.api.service.LegalGuardianService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.LegalGuardianDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;

public class LegalGuardianRepository {
  private static LegalGuardianRepository instance;
  private static LegalGuardianService legalGuardianService;
  private final LegalGuardianDAO legalGuardianDao;

  private LegalGuardianRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    legalGuardianDao = appDatabase.legalGuardianDAO();
    legalGuardianService = LegalGuardianService.getInstanceService(application);
  }

  public static LegalGuardianRepository getInstance(Application application) {
    if (instance == null) {
      instance = new LegalGuardianRepository(application);
    }

    return instance;
  }

  public void insert(LegalGuardian newLegalGuardian) {
    if (newLegalGuardian != null) {
      LegalGuardian legalGuardian = legalGuardianDao.getLegalGuardian(newLegalGuardian.getPerson());

      if (legalGuardian == null) {
        legalGuardianDao.insert(newLegalGuardian);
      } else {
        legalGuardianDao.update(newLegalGuardian);
      }
    }
  }

  public LegalGuardian getLegalGuardian(String id) {
    return legalGuardianDao.getLegalGuardian(id);
  }

  public LegalGuardian loginLegalGuardian(Login login) throws ApiException {
    return legalGuardianService.loginCall(login);
  }

  public void setNewPassword(LegalGuardian legalGuardian) throws ApiException {
    LegalGuardian updateLegalGuardian = legalGuardianService.updateLegalGuardianCall(legalGuardian);
    insert(updateLegalGuardian);
  }
}
