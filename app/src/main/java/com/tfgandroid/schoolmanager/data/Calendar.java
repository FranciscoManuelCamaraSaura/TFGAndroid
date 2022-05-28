/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 15/09/20 20:50 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Calendar.java.
 * Last modified: 15/09/20 20:43.
 */

package com.tfgandroid.schoolmanager.data;

import androidx.annotation.NonNull;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import java.util.List;
import java.util.Objects;

public class Calendar {
  private List<Event> events;

  public Calendar(List<Event> events) {
    this.events = events;
  }

  public List<Event> getDate() {
    return events;
  }

  public void setDate(List<Event> events) {
    this.events = events;
  }

  public List<Event> addEvent(Event event) {
    events.add(event);

    return events;
  }

  public Event getEvent(int i) {
    return events.get(i);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof Calendar)) return false;

    Calendar calendar = (Calendar) o;

    return Objects.equals(getDate(), calendar.getDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDate());
  }

  @NonNull
  @Override
  public String toString() {
    return "Calendar {" + "events = " + events + '}';
  }
}
