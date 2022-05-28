/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Manager.java.
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
import com.tfgandroid.schoolmanager.data.access.database.convert.AdminTypeConvert;
import com.tfgandroid.schoolmanager.data.enums.Admin;
import java.util.Objects;

@Entity(
    tableName = "manager",
    foreignKeys = {
      @ForeignKey(
          entity = Person.class,
          parentColumns = "dni",
          childColumns = "person",
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = School.class,
          parentColumns = "id",
          childColumns = "school",
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index("school"), @Index("person")})
public class Manager {
  @PrimaryKey @NonNull private String person;
  private int school;

  @TypeConverters(AdminTypeConvert.class)
  @NonNull
  private Admin type_admin;

  public Manager(@NonNull String person, int school, @NonNull Admin type_admin) {
    this.person = person;
    this.school = school;
    this.type_admin = type_admin;
  }

  @NonNull
  public String getPerson() {
    return person;
  }

  public void setPerson(@NonNull String person) {
    this.person = person;
  }

  public int getSchool() {
    return school;
  }

  public void setSchool(int school) {
    this.school = school;
  }

  @NonNull
  public Admin getType_admin() {
    return type_admin;
  }

  public void setType_admin(@NonNull Admin type_admin) {
    this.type_admin = type_admin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Manager)) {
      return false;
    }

    Manager manager = (Manager) o;

    return getSchool() == manager.getSchool()
        && getPerson().equals(manager.getPerson())
        && getType_admin().equals(manager.getType_admin());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPerson(), getSchool(), getType_admin());
  }

  @NonNull
  @Override
  public String toString() {
    return "Manager{"
        + "person='"
        + person
        + '\''
        + ", school="
        + school
        + ", typeAdmin="
        + type_admin
        + '}';
  }
}
