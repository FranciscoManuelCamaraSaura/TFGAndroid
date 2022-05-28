/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 12/9/21 10:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: NotificationBuilder.java.
 * Last modified: 12/9/21 10:43.
 */

package com.tfgandroid.schoolmanager.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.tfg.activity.Launcher;
import com.tfgandroid.schoolmanager.tfg.fragment.ReceiverMessage;

public class NotificationsChannel extends ContextWrapper {
  private static final String CHANNEL_ID = "com.tfgandroid.schoolmanager";
  private static final String CHANNEL_NAME = "@string/app_name";
  private static final String MESSAGE_ID = "messageId";
  private NotificationManager notificationManager;

  public NotificationsChannel(Context context) {
    super(context);

    createNotificationChannel();
  }

  public void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      android.app.NotificationChannel notificationChannel =
          new android.app.NotificationChannel(
              CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

      // notificationChannel.setDescription(description);
      getManager().createNotificationChannel(notificationChannel);
    }
  }

  private NotificationManager getManager() {
    if (notificationManager == null) {
      notificationManager = getSystemService(NotificationManager.class);
      // notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    return notificationManager;
  }

  public void createNotification(Context context, Bundle bundle, String title, String content) {
    Intent intent = new Intent(this, ReceiverMessage.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    PendingIntent pendingIntent =
        new NavDeepLinkBuilder(context)
            .setComponentName(Launcher.class)
            .setGraph(R.navigation.navigation)
            .setDestination(R.id.receiverMessage)
            .setArguments(bundle)
            .createPendingIntent();

    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

    showNotification(builder, bundle);
  }

  public void showNotification(NotificationCompat.Builder builder, Bundle bundle) {
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

    notificationManager.notify(bundle.getInt(MESSAGE_ID), builder.build());
  }
}
