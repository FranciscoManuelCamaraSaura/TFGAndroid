/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/05/21 16:34 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: NewMessageViewModel.java.
 * Last modified: 19/05/21 16:32.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.repository.ImpartRepository;
import com.tfgandroid.schoolmanager.data.repository.ManagerRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import com.tfgandroid.schoolmanager.data.repository.SubjectRepository;
import java.util.ArrayList;
import java.util.List;

public class NewMessageViewModel extends AndroidViewModel {
  private final ImpartRepository impartRepository;
  private final ManagerRepository managerRepository;
  private final PersonRepository personRepository;
  private final SubjectRepository subjectRepository;

  public NewMessageViewModel(Application application) {
    super(application);

    impartRepository = ImpartRepository.getInstance(application);
    managerRepository = ManagerRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
    subjectRepository = SubjectRepository.getInstance(application);
  }

  public MutableLiveData<List<Subject>> getSubjects() {
    MutableLiveData<List<Subject>> mutableLiveData = new MutableLiveData<>();

    new Thread(() -> mutableLiveData.postValue(subjectRepository.getSubjects())).start();

    return mutableLiveData;
  }

  public MutableLiveData<List<Manager>> getManagers() {
    MutableLiveData<List<Manager>> mutableLiveData = new MutableLiveData<>();

    new Thread(() -> mutableLiveData.postValue(managerRepository.getManagers())).start();

    return mutableLiveData;
  }

  public MutableLiveData<List<Manager>> getManagers(String adminType) {
        MutableLiveData<List<Manager>> mutableLiveData = new MutableLiveData<>();

        new Thread(() -> mutableLiveData.postValue(managerRepository.getManagers(adminType))).start();

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

  public MutableLiveData<Person> getImpart(String subjectCode) {
    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () ->
                mutableLiveData.postValue(
                    personRepository.getPersonSaved(impartRepository.getTeacher(subjectCode))))
        .start();

    return mutableLiveData;
  }
}
