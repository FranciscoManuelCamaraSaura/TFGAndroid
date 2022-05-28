/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:47 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ReceiverMessageViewModel.java.
 * Last modified: 26/05/21 16:17.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.repository.ImpartRepository;
import com.tfgandroid.schoolmanager.data.repository.MessageRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import com.tfgandroid.schoolmanager.data.repository.SubjectRepository;
import java.util.ArrayList;
import java.util.List;

public class ReceiverMessageViewModel extends AndroidViewModel {
  private final ImpartRepository impartRepository;
  private final MessageRepository messageRepository;
  private final PersonRepository personRepository;
  private final SubjectRepository subjectRepository;
  private TypeError type;

  public ReceiverMessageViewModel(Application application) {
    super(application);

    impartRepository = ImpartRepository.getInstance(application);
    messageRepository = MessageRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
    subjectRepository = SubjectRepository.getInstance(application);
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

  public MutableLiveData<Subject> getSubject(String teacher) {
    MutableLiveData<Subject> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<String> subjects = impartRepository.getSubject(teacher);
              Subject subject = subjectRepository.getSubject(subjects.get(0));

              mutableLiveData.postValue(subject);
            })
        .start();

    return mutableLiveData;
  }

  public void setReadMessage(Message message) {
    new Thread(
            () -> {
              try {
                message.setRead(true);
                messageRepository.readMessage(message);
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
