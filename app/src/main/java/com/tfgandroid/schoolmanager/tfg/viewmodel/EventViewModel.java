/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 6/6/22 19:44 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventViewModel.java.
 * Last modified: 6/6/22 19:39.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
import com.tfgandroid.schoolmanager.data.repository.EventRepository;
import com.tfgandroid.schoolmanager.data.repository.ExamRepository;
import com.tfgandroid.schoolmanager.data.repository.MakesRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import com.tfgandroid.schoolmanager.data.repository.SubjectRepository;

public class EventViewModel extends AndroidViewModel {
  private final EventRepository eventRepository;
  private final ExamRepository examRepository;
  private final MakesRepository makesRepository;
  private final PersonRepository personRepository;
  private final SubjectRepository subjectRepository;
  private TypeError type;

  public EventViewModel(Application application) {
    super(application);

    eventRepository = EventRepository.getInstance(application);
    examRepository = ExamRepository.getInstance(application);
    makesRepository = MakesRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
    subjectRepository = SubjectRepository.getInstance(application);
  }

  public MutableLiveData<Event> getEvent(int event_id) {
    MutableLiveData<Event> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Event event = eventRepository.getEvent(event_id);

              mutableLiveData.postValue(event);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Exam> getExam(int exam_id) {
    MutableLiveData<Exam> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Exam exam = examRepository.getExam(exam_id);

              mutableLiveData.postValue(exam);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Double> getNote(int exam) {
    MutableLiveData<Double> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Double note = makesRepository.getNote(exam);

              mutableLiveData.postValue(note);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<String> getResponsible(String person) {
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              String responsible = personRepository.getPersonName(person);

              mutableLiveData.postValue(responsible);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<String> getSubject(String code) {
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              String subject = subjectRepository.getSubjectName(code);

              mutableLiveData.postValue(subject);
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
