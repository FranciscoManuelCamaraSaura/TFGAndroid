/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Message.java.
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
    tableName = "message",
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
public class Message {
  @PrimaryKey private final int id;

  @TypeConverters(DataTypeConvert.class)
  @NonNull
  private Date date;

  private String matter;
  private String text;

  @NonNull private String sender;
  @NonNull private String receiver;

  private int previous_message;
  private boolean read;
  private boolean reply;

  public Message(
      Integer id,
      @NonNull Date date,
      String matter,
      String text,
      @NonNull String sender,
      @NonNull String receiver,
      int previous_message,
      boolean read,
      boolean reply) {
    this.id = id;
    this.date = date;
    this.matter = matter;
    this.text = text;
    this.sender = sender;
    this.receiver = receiver;
    this.previous_message = previous_message;
    this.read = read;
    this.reply = reply;
  }

  public int getId() {
    return id;
  }

  @NonNull
  public Date getDate() {
    return date;
  }

  public void setDate(@NonNull Date date) {
    this.date = date;
  }

  public String getMatter() {
    return matter;
  }

  public void setMatter(String matter) {
    this.matter = matter;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
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

  public int getPrevious_message() {
    return previous_message;
  }

  public void setPrevious_message(int previous_message) {
    this.previous_message = previous_message;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public boolean isReply() {
    return reply;
  }

  public void setReply(boolean reply) {
    this.reply = reply;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Message)) {
      return false;
    }

    Message message = (Message) o;

    return getId() == message.getId()
        && getPrevious_message() == message.getPrevious_message()
        && isRead() == message.isRead()
        && isReply() == message.isReply()
        && getDate().equals(message.getDate())
        && getMatter().equals(message.getMatter())
        && getText().equals(message.getText())
        && getSender().equals(message.getSender())
        && getReceiver().equals(message.getReceiver());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getDate(),
        getMatter(),
        getText(),
        getSender(),
        getReceiver(),
        getPrevious_message(),
        isRead(),
        isReply());
  }

  @NonNull
  @Override
  public String toString() {
    return "Message{"
        + "id="
        + id
        + ", date="
        + date
        + ", matter='"
        + matter
        + '\''
        + ", text='"
        + text
        + '\''
        + ", sender='"
        + sender
        + '\''
        + ", receiver='"
        + receiver
        + '\''
        + ", previous_message="
        + previous_message
        + ", read="
        + read
        + ", reply="
        + reply
        + '}';
  }
}
