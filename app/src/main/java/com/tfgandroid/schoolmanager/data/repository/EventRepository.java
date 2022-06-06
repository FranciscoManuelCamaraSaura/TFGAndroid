/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 17/03/21 19:31 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventRepository.java.
 * Last modified: 17/03/21 19:31.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.EventService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.EventDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SchoolDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import java.util.List;

public class EventRepository {
  private static EventRepository instance;
  private static EventService eventService;
  private final EventDAO eventDao;
  private final SchoolDAO schoolDao;

  private EventRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    schoolDao = appDatabase.schoolDAO();
    eventDao = appDatabase.eventDAO();
    eventService = EventService.getInstanceService(application);
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

  public void getEvents(int school, int student) throws ApiException {
    List<Event> events = eventService.getEventsCall(school, student);

    for (Event event : events) {
      insert(event);
    }
  }

  public Event getEvent(int event) {
    return eventDao.getEvent(event);
  }

  public List<Event> getEvents() {
    return eventDao.getAllEvents();
  }
}
