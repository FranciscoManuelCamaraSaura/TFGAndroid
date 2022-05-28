/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/05/21 19:41 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SentMessageViewModel.java.
 * Last modified: 22/05/21 19:41.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.repository.MessageRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;

public class SentMessageViewModel extends AndroidViewModel {
  private final MessageRepository messageRepository;
  private final PersonRepository personRepository;
  private TypeError type;

  public SentMessageViewModel(Application application) {
    super(application);

    messageRepository = MessageRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
  }

  public MutableLiveData<List<Person>> getPersons() {
    MutableLiveData<List<Person>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Person> person = new ArrayList<>();

              person.addAll(personRepository.getTeachersSaved());
              person.addAll(personRepository.getManagersSaved());

              mutableLiveData.postValue(person);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Message> getMessage(int messageId) {
    MutableLiveData<Message> mutableLiveData = new MutableLiveData<>();

    new Thread(() -> mutableLiveData.postValue(messageRepository.getMessage(messageId))).start();

    return mutableLiveData;
  }

  public void updateData(Message message) {
    new Thread(
            () -> {
              try {
                message.setRead(true);
                messageRepository.updateData(message);
              } catch (ApiException e) {
                setType(e.getType());
              }
            })
        .start();
  }

  public TypeError getType() {
    return type;
  }

  public void setType(TypeError type) {
    this.type = type;
  }
}
