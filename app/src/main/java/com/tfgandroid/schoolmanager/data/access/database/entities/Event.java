/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Event.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.tfgandroid.schoolmanager.data.access.database.convert.DataTypeConvert;
import java.sql.Date;
import java.util.Objects;

@Entity(
    tableName = "event",
    foreignKeys = {
      @ForeignKey(
          entity = School.class,
          parentColumns = "id",
          childColumns = "school",
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = Person.class,
          parentColumns = {"dni"},
          childColumns = {"responsible"},
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index({"id"}), @Index("school"), @Index("responsible")})
public class Event {
  @PrimaryKey private final int id;
  private String name;
  private String description;
  private int duration;

  @TypeConverters(DataTypeConvert.class)
  @NonNull
  private Date date;

  private int school;
  private String responsible;

  public Event(
      int id,
      String name,
      String description,
      int duration,
      @NonNull Date date,
      int school,
      String responsible) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.duration = duration;
    this.date = date;
    this.school = school;
    this.responsible = responsible;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @NonNull
  public Date getDate() {
    return date;
  }

  public void setDate(@NonNull Date date) {
    this.date = date;
  }

  public int getSchool() {
    return school;
  }

  public void setSchool(int school) {
    this.school = school;
  }

  public String getResponsible() {
    return responsible;
  }

  public void setResponsible(String responsible) {
    this.responsible = responsible;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Event)) {
      return false;
    }
    Event event = (Event) o;
    return getId() == event.getId()
        && getDuration() == event.getDuration()
        && getSchool() == event.getSchool()
        && getName().equals(event.getName())
        && getDescription().equals(event.getDescription())
        && getDate().equals(event.getDate())
        && getResponsible().equals(event.getResponsible());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getName(),
        getDescription(),
        getDuration(),
        getDate(),
        getSchool(),
        getResponsible());
  }

    @NonNull
  @Override
  public String toString() {
    return "Event{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", duration="
        + duration
        + ", date="
        + date
        + ", school="
        + school
        + ", responsible='"
        + responsible
        + '\''
        + '}';
  }
}
