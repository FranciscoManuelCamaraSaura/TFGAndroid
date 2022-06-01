/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 1/6/22 20:12 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertsViewModel.java.
 * Last modified: 1/6/22 20:12.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.repository.AlertRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import java.util.List;

public class AlertsViewModel extends AndroidViewModel {
  private final AlertRepository alertRepository;
  private final PersonRepository personRepository;
  private TypeError type;

  public AlertsViewModel(Application application) {
    super(application);

    alertRepository = AlertRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
  }

  public MutableLiveData<List<Alert>> getAlerts(LegalGuardian legalGuardian) {
    MutableLiveData<List<Alert>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                List<Alert> alerts = alertRepository.getAlertsReceived(legalGuardian.getPerson());

                mutableLiveData.postValue(alerts);
              } catch (ApiException e) {
                setType(e.getType());
                mutableLiveData.postValue(null);
              }
            })
        .start();

    return mutableLiveData;
  }

  public void setReadAlert(Alert alert) {
    new Thread(
            () -> {
              try {
                alert.setRead(true);
                alertRepository.readAlert(alert);
              } catch (ApiException e) {
                setType(e.getType());
              }
            })
        .start();
  }

  public MutableLiveData<Person> getPerson(LegalGuardian legalGuardian) {
    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Person person = personRepository.getPersonSaved(legalGuardian.getPerson());

              mutableLiveData.postValue(person);
            })
        .start();

    return mutableLiveData;
  }

  public TypeError getType() {
    return type;
  }

  public void setType(TypeError type) {
    this.type = type;
  }
}
