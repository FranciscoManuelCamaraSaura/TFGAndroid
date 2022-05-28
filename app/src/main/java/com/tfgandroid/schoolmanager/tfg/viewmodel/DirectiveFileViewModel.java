/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 2/06/21 17:44 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DirectiveFileViewModel.java.
 * Last modified: 2/06/21 17:44.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.repository.ManagerRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;

public class DirectiveFileViewModel extends AndroidViewModel {
  private final ManagerRepository managerRepository;
  private final PersonRepository personRepository;

  public DirectiveFileViewModel(Application application) {
    super(application);

    managerRepository = ManagerRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
  }

  public MutableLiveData<Person> getPerson(String person) {
    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Person manager = personRepository.getPersonSaved(person);

              mutableLiveData.postValue(manager);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Manager> getManager(String person) {
    MutableLiveData<Manager> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Manager manager = managerRepository.getManager(person);

              mutableLiveData.postValue(manager);
            })
        .start();

    return mutableLiveData;
  }
}
