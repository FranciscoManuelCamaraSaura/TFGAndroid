/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 12/05/21 19:22 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TeacherFileViewModel.java.
 * Last modified: 12/05/21 19:22.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import java.util.List;

public class TeacherFileViewModel extends AndroidViewModel {
  private final PersonRepository personRepository;

  public TeacherFileViewModel(Application application) {
    super(application);

    personRepository = PersonRepository.getInstance(application);
  }

  public MutableLiveData<Person> getTeacher(String person) {
    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Person teacher = personRepository.getPersonSaved(person);

              mutableLiveData.postValue(teacher);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<List<Subject>> getSubject(String person) {
    MutableLiveData<List<Subject>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Subject> subject = personRepository.getSubject(person);

              mutableLiveData.postValue(subject);
            })
        .start();

    return mutableLiveData;
  }
}
