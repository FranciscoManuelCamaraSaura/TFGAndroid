/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 20:23 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MessageRepository.java.
 * Last modified: 3/03/21 20:23.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.MessageService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.MessageDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.PersonDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository {
  private static MessageRepository instance;
  private static MessageService messageService;
  private final PersonDAO personDao;
  private final MessageDAO messageDao;

  private MessageRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    personDao = appDatabase.personDAO();
    messageDao = appDatabase.messageDAO();
    messageService = MessageService.getInstanceService(application);
  }

  public static MessageRepository getInstance(Application application) {
    if (instance == null) {
      instance = new MessageRepository(application);
    }

    return instance;
  }

  public void insert(Message newMessage) {
    if (newMessage != null) {
      Person sender = personDao.getPerson(newMessage.getSender());
      Person receiver = personDao.getPerson(newMessage.getReceiver());

      if (sender != null && receiver != null && sender != receiver) {
        Message message = messageDao.getMessage(newMessage.getId());

        if (message == null) {
          messageDao.insert(newMessage);
        } else {
          messageDao.update(newMessage);
        }
      }
    }
  }

  public Message getMessage(Integer message_id) {
    return messageDao.getMessage(message_id);
  }

  public Message getMessage(Integer message_id, List<Message> messageThread) {
    Message message = getMessage(message_id);

    if (message.getPrevious_message() != message.getId()) {
      messageThread.add(getMessage(message_id, messageThread));
    }

    return message;
  }

  public List<Message> getMessageSent(int message_id) {
    List<Message> messageThread = new ArrayList<>();
    messageThread.add(getMessage(message_id, messageThread));

    return messageThread;
  }

  public List<Message> getMessagesSent(String sender) throws ApiException {
    List<Message> messagesSent = messageService.getSentMessages(sender);

    for (Message message : messagesSent) {
      insert(message);
    }

    return messagesSent;
  }

  public List<Message> getMessageReceived(int message_id) {
    List<Message> messageThread = new ArrayList<>();
    messageThread.add(getMessage(message_id, messageThread));

    return messageThread;
  }

  public List<Message> getMessagesReceived(String receiver) throws ApiException {
    List<Message> messagesReceived = messageService.getReceivedMessages(receiver);

    for (Message message : messagesReceived) {
      insert(message);
    }

    return messagesReceived;
  }

  public List<Message> getMessagesReceivedSaved(String receiver) {
    return messageDao.getMessagesReceived(receiver);
  }

  public List<Message> getMessagesReceivedLive(String receiver) throws ApiException {
    return messageService.getReceivedMessages(receiver);
  }

  public void sendMessage(Message message) throws ApiException {
    insert(messageService.sendMessage(message));

    if (message.getPrevious_message() != 0) {
      Message previousMessage = getMessage(message.getPrevious_message());

      previousMessage.setRead(true);
      replyMessage(previousMessage);
    }
  }

  public void readMessage(Message message) throws ApiException {
    insert(messageService.readMessage(message));
  }

  public void replyMessage(Message message) throws ApiException {
    insert(messageService.replyMessage(message));
  }

  public void updateData(Message message) throws ApiException {
    insert(messageService.updateData(message));
  }
}
