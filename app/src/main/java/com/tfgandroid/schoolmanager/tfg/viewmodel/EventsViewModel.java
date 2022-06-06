/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/6/22 19:23 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventsViewModel.java.
 * Last modified: 5/6/22 19:23.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.data.repository.EventRepository;
import java.util.List;

public class EventsViewModel extends AndroidViewModel {
  private final EventRepository eventRepository;
  private TypeError type;

  public EventsViewModel(Application application) {
    super(application);

    eventRepository = EventRepository.getInstance(application);
  }

  public MutableLiveData<List<Event>> getEvents() {
    MutableLiveData<List<Event>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Event> events = eventRepository.getEvents();

              mutableLiveData.postValue(events);
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
