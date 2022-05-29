/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 18:22 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertRepository.java.
 * Last modified: 29/5/22 13:22.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.AlertService;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.AlertDAO;

public class AlertRepository {
  private static AlertRepository instance;
  private static AlertService instanceService;
  private static AlertDAO alertDAO;

  private AlertRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    alertDAO = appDatabase.alertDAO();
    instanceService = AlertService.getInstanceService(application);
  }
}
