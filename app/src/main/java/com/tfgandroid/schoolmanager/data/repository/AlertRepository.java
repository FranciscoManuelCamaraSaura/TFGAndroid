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
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.AlertDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.PersonDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import java.util.List;

public class AlertRepository {
  private static AlertRepository instance;
  private static AlertService alertService;
  private static AlertDAO alertDAO;
  private final PersonDAO personDao;

  private AlertRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    alertDAO = appDatabase.alertDAO();
    personDao = appDatabase.personDAO();
    alertService = AlertService.getInstanceService(application);
  }

  public static AlertRepository getInstance(Application application) {
    if (instance == null) {
      instance = new AlertRepository(application);
    }

    return instance;
  }

  public void insert(Alert newAlert) {
    if (newAlert != null) {
      Person sender = personDao.getPerson(newAlert.getSender());
      Person receiver = personDao.getPerson(newAlert.getReceiver());

      if (sender != null && receiver != null && sender != receiver) {
        Alert alert = alertDAO.getAlert(newAlert.getId());

        if (alert == null) {
          alertDAO.insert(newAlert);
        } else {
          alertDAO.update(newAlert);
        }
      }
    }
  }

  public void delete(Alert alert) {
    alertDAO.delete(alert);
  }

  public List<Alert> getAlertsReceived(String receiver) throws ApiException {
    List<Alert> alertsReceived = alertService.getReceivedAlerts(receiver);

    for (Alert alert : alertsReceived) {
      insert(alert);
    }

    return alertsReceived;
  }

  public void readAlert(Alert alert) throws ApiException {
    delete(alertService.readAlert(alert));
  }
}
