/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:35 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Alert.java.
 * Last modified: 29/5/22 12:02.
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
    tableName = "alert",
    foreignKeys = {
      @ForeignKey(
          entity = Person.class,
          parentColumns = "dni",
          childColumns = "sender",
          onDelete = CASCADE,
          onUpdate = CASCADE),
      @ForeignKey(
          entity = Person.class,
          parentColumns = "dni",
          childColumns = "receiver",
          onDelete = CASCADE,
          onUpdate = CASCADE)
    },
    indices = {@Index("id"), @Index("sender"), @Index("receiver")})
public class Alert {
  @PrimaryKey private final int id;

  @TypeConverters(DataTypeConvert.class)
  @NonNull
  private Date send_date;

  @TypeConverters(DataTypeConvert.class)
  @NonNull
  private Date read_date;

  private String matter;

  @NonNull private String sender;
  @NonNull private String receiver;

  private boolean read;

  public Alert(
      int id,
      @NonNull Date send_date,
      @NonNull Date read_date,
      String matter,
      @NonNull String sender,
      @NonNull String receiver,
      boolean read) {
    this.id = id;
    this.send_date = send_date;
    this.read_date = read_date;
    this.matter = matter;
    this.sender = sender;
    this.receiver = receiver;
    this.read = read;
  }

  public int getId() {
    return id;
  }

  @NonNull
  public Date getSend_date() {
    return send_date;
  }

  public void setSend_date(@NonNull Date send_date) {
    this.send_date = send_date;
  }

  @NonNull
  public Date getRead_date() {
    return read_date;
  }

  public void setRead_date(@NonNull Date read_date) {
    this.read_date = read_date;
  }

  public String getMatter() {
    return matter;
  }

  public void setMatter(String matter) {
    this.matter = matter;
  }

  @NonNull
  public String getSender() {
    return sender;
  }

  public void setSender(@NonNull String sender) {
    this.sender = sender;
  }

  @NonNull
  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(@NonNull String receiver) {
    this.receiver = receiver;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Alert)) {
      return false;
    }
    Alert alert = (Alert) o;
    return getId() == alert.getId()
        && isRead() == alert.isRead()
        && getSend_date().equals(alert.getSend_date())
        && getRead_date().equals(alert.getRead_date())
        && getMatter().equals(alert.getMatter())
        && getSender().equals(alert.getSender())
        && getReceiver().equals(alert.getReceiver());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(), getSend_date(), getRead_date(), getMatter(), getSender(), getReceiver(), isRead());
  }

  @NonNull
  @Override
  public String toString() {
    return "Alert{"
        + "id="
        + id
        + ", send_date="
        + send_date
        + ", read_date="
        + read_date
        + ", matter='"
        + matter
        + '\''
        + ", sender='"
        + sender
        + '\''
        + ", receiver='"
        + receiver
        + '\''
        + ", read="
        + read
        + '}';
  }
}
