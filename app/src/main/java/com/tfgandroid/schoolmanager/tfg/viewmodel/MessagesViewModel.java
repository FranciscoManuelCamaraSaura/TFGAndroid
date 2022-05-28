/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 12/05/21 19:53 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MessagesViewModel.java.
 * Last modified: 12/05/21 19:53.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.repository.MessageRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;

public class MessagesViewModel extends AndroidViewModel {
  private final MessageRepository messageRepository;
  private final PersonRepository personRepository;
  private TypeError type;

  public MessagesViewModel(Application application) {
    super(application);

    messageRepository = MessageRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
  }

  public MutableLiveData<List<Message>> getMessages(LegalGuardian legalGuardian, boolean sent) {
    MutableLiveData<List<Message>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                List<Message> messages;

                if (sent) {
                  messages = messageRepository.getMessagesSent(legalGuardian.getPerson());
                } else {
                  messages = messageRepository.getMessagesReceived(legalGuardian.getPerson());
                }

                mutableLiveData.postValue(messages);
              } catch (ApiException e) {
                setType(e.getType());
                mutableLiveData.postValue(null);
              }
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Person> getPerson(LegalGuardian legalGuardian) {
    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Person person = personRepository.getPersonSaved(legalGuardian.getPerson());

              mutableLiveData.postValue(person);
            })
        .start();

    return mutableLiveData;
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

  public TypeError getType() {
    return type;
  }

  public void setType(TypeError type) {
    this.type = type;
  }
}
