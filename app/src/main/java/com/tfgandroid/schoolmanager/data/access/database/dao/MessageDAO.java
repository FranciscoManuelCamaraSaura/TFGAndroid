/*
 * Copyright (c) 2020. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 9/10/20 11:37 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MessageDAO.java.
 * Last modified: 9/10/20 11:37.
 */

package com.tfgandroid.schoolmanager.data.access.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import java.util.List;

@Dao
public interface MessageDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Message message);

  @Update
  void update(Message message);

  @Delete
  void delete(Message message);

  @Query("SELECT * FROM message WHERE id = :message_id")
  Message getMessage(int message_id);

  @Query("SELECT * FROM message WHERE sender = :sender_dni")
  List<Message> getMessagesSent(String sender_dni);

  @Query("SELECT * FROM message WHERE receiver = :receiver_dni")
  List<Message> getMessagesReceived(String receiver_dni);
}
