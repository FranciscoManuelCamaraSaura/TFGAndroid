/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: LegalGuardian.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Objects;

@Entity(
    tableName = "legal_guardian",
    foreignKeys =
        @ForeignKey(
            entity = Person.class,
            parentColumns = "dni",
            childColumns = "person",
            onDelete = CASCADE,
            onUpdate = CASCADE),
    indices = @Index(value = "person", unique = true))
public class LegalGuardian {
  @PrimaryKey private final int id;
  private @NonNull String person;
  private String user_name;
  private String password;

  @Ignore private String student_name;

  public LegalGuardian(int id, @NonNull String person, String user_name, String password) {
    this.id = id;
    this.person = person;
    this.user_name = user_name;
    this.password = password;
    this.student_name = "";
  }

  public int getId() {
    return id;
  }

  @NonNull
  public String getPerson() {
    return person;
  }

  public void setPerson(@NonNull String person) {
    this.person = person;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getStudent_name() {
    return student_name;
  }

  public void setStudent_name(String student_name) {
    this.student_name = student_name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LegalGuardian)) {
      return false;
    }
    LegalGuardian that = (LegalGuardian) o;
    return getId() == that.getId()
        && getPerson().equals(that.getPerson())
        && getUser_name().equals(that.getUser_name())
        && getPassword().equals(that.getPassword())
        && getStudent_name().equals(that.getStudent_name());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getPerson(), getUser_name(), getPassword(), getStudent_name());
  }

  @NonNull
  @Override
  public String toString() {
    return "LegalGuardian{"
        + "id="
        + id
        + ", person='"
        + person
        + '\''
        + ", user_name='"
        + user_name
        + '\''
        + ", password='"
        + password
        + '\''
        + ", student_name='"
        + student_name
        + '\''
        + '}';
  }
}
