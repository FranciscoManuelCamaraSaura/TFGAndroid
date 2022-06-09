/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 10/9/21 19:30 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: LauncherViewModel.java.
 * Last modified: 10/9/21 19:30.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.repository.AlertRepository;
import com.tfgandroid.schoolmanager.data.repository.MessageRepository;
import java.util.List;

public class LauncherViewModel extends AndroidViewModel {
  private static final String TAG = "LauncherViewModel";
  private final AlertRepository alertRepository;
  private final MessageRepository messageRepository;
  private TypeError type;

  public LauncherViewModel(Application application) {
    super(application);

    alertRepository = AlertRepository.getInstance(application);
    messageRepository = MessageRepository.getInstance(application);
  }

  public MutableLiveData<List<Message>> getMessagesSaved(LegalGuardian legalGuardian) {
    MutableLiveData<List<Message>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Message> messages =
                  messageRepository.getMessagesReceivedSaved(legalGuardian.getPerson());

              mutableLiveData.postValue(messages);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<List<Message>> getMessages(LegalGuardian legalGuardian) {
    MutableLiveData<List<Message>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                List<Message> messages =
                    messageRepository.getMessagesReceivedLive(legalGuardian.getPerson());

                Log.i(TAG, String.valueOf(messages.size()));

                mutableLiveData.postValue(messages);
              } catch (ApiException e) {
                setType(e.getType());
                mutableLiveData.postValue(null);
              }
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<List<Alert>> getAlertsSaved(LegalGuardian legalGuardian) {
    MutableLiveData<List<Alert>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Alert> alerts =
                  alertRepository.getAlertsReceivedSaved(legalGuardian.getPerson());

              Log.i(TAG, String.valueOf(alerts.size()));

              mutableLiveData.postValue(alerts);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<List<Alert>> getAlerts(LegalGuardian legalGuardian) {
    MutableLiveData<List<Alert>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                List<Alert> alerts =
                    alertRepository.getAlertsReceivedLive(legalGuardian.getPerson());

                Log.i(TAG, String.valueOf(alerts.size()));

                mutableLiveData.postValue(alerts);
              } catch (ApiException e) {
                setType(e.getType());
                mutableLiveData.postValue(null);
              }
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
