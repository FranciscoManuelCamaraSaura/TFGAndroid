/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 17/03/21 19:31 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventRepository.java.
 * Last modified: 17/03/21 19:31.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.EventDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SchoolDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;

public class EventRepository {
  private static EventRepository instance;
  private final SchoolDAO schoolDao;
  private final EventDAO eventDao;

  private EventRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    schoolDao = appDatabase.schoolDAO();
    eventDao = appDatabase.eventDAO();
  }

  public static EventRepository getInstance(Application application) {
    if (instance == null) {
      instance = new EventRepository(application);
    }

    return instance;
  }

  public void insert(Event newEvent) {
    if (newEvent != null) {
      School school = schoolDao.getSchool(newEvent.getSchool());

      if (school != null) {
        Event events = eventDao.getEvent(newEvent.getId(), school.getId());

        if (events == null) {
          eventDao.insert(newEvent);
        } else {
          eventDao.update(newEvent);
        }
      }
    }
  }
}