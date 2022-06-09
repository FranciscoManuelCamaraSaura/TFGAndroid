/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 13/10/21 11:21 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: NotificationService.java.
 * Last modified: 13/10/21 11:21.
 */

package com.tfgandroid.schoolmanager.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.tfg.viewmodel.LauncherViewModel;
import java.util.List;

public class NotificationService extends Service {
  public static final String ALERT_ID = "alertId";
  private static final String MESSAGE_ID = "messageId";
  private static final String TAG = NotificationService.class.getSimpleName();
  private final IBinder notificationBinder = new NotificationBinder();
  private boolean isServiceRunning;
  private List<Alert> alertsSaved;
  private List<Message> messagesSaved;

  public NotificationService() {}

  @Override
  public IBinder onBind(Intent intent) {
    return notificationBinder;
  }

  @Override
  public void onCreate() {
    Log.d(TAG, "Servicio creado");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG, "Servicio iniciado");

    return START_NOT_STICKY;
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "Servicio destruido");
  }

  public void startService(
      LifecycleOwner lifecycleOwner,
      LauncherViewModel launcherViewModel,
      LegalGuardian legalGuardian) {
    if (!isServiceRunning()) {
      isServiceRunning = true;
    } else {
      Log.e(TAG, "startService request for an already running Service");
    }

    if (launcherViewModel != null) {
      if (launcherViewModel.getMessages(legalGuardian).hasObservers()) {
        checkMessages(lifecycleOwner, launcherViewModel, legalGuardian);
      }

      if (launcherViewModel.getAlerts(legalGuardian).hasObservers()) {
        checkAlerts(lifecycleOwner, launcherViewModel, legalGuardian);
      }
    }
  }

  public void checkMessages(
      LifecycleOwner lifecycleOwner,
      LauncherViewModel launcherViewModel,
      LegalGuardian legalGuardian) {
    launcherViewModel
        .getMessagesSaved(legalGuardian)
        .removeObserver(messages -> messagesSaved = null);

    launcherViewModel
        .getMessagesSaved(legalGuardian)
        .observe(lifecycleOwner, messages -> messagesSaved = messages);

    launcherViewModel
        .getMessages(legalGuardian)
        .observeForever(
            messages -> {
              Message message = messages.get(messages.size() - 1);

              if (!messagesSaved.contains(message) && !message.isRead()) {
                setMessageNotification(message);
              }
            });
  }

  public void checkAlerts(
      LifecycleOwner lifecycleOwner,
      LauncherViewModel launcherViewModel,
      LegalGuardian legalGuardian) {
    launcherViewModel.getAlertsSaved(legalGuardian).removeObserver(alerts -> alertsSaved = null);

    launcherViewModel.getAlertsSaved(legalGuardian).observe(lifecycleOwner, alerts -> alertsSaved = alerts);

    launcherViewModel
        .getAlerts(legalGuardian)
        .observeForever(
            alerts -> {
              Alert alert = alerts.get(alerts.size() - 1);

              if (!alertsSaved.contains(alert) && !alert.isRead()) {
                setAlertNotification(alert);
              }
            });
  }

  public void stopService(LauncherViewModel launcherViewModel, LegalGuardian legalGuardian) {
    if (isServiceRunning()) {
      isServiceRunning = false;
      launcherViewModel
          .getMessagesSaved(legalGuardian)
          .removeObserver(messages -> messagesSaved = null);
    } else {
      Log.e(TAG, "stopTimer request for a timer that isn't running");
    }
  }

  public boolean isServiceRunning() {
    return isServiceRunning;
  }

  private void setMessageNotification(Message message) {
    NotificationsChannel notification = new NotificationsChannel(this);
    Bundle bundle = new Bundle();

    bundle.putInt(MESSAGE_ID, message.getId());

    notification.createMessageNotification(this, bundle, message.getMatter(), message.getText());
  }

  private void setAlertNotification(Alert alert) {
    NotificationsChannel notification = new NotificationsChannel(this);
    Bundle bundle = new Bundle();

    bundle.putInt(ALERT_ID, alert.getId());

    notification.createAlertNotification(this, bundle, alert.getMatter(), alert.getSend_date());
  }

  public void background() {
    stopForeground(true);
  }

  public class NotificationBinder extends Binder {
    public NotificationService getService() {
      return NotificationService.this;
    }
  }
}
