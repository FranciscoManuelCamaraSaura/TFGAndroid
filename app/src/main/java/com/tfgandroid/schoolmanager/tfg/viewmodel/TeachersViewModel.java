/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/8/21 17:26 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TeachersViewModel.java.
 * Last modified: 29/8/21 17:26.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import java.util.List;

public class TeachersViewModel extends AndroidViewModel {
  private final PersonRepository personRepository;
  private TypeError type;

  public TeachersViewModel(Application application) {
    super(application);

    personRepository = PersonRepository.getInstance(application);
  }

  public MutableLiveData<List<Person>> getTeachers() {
    MutableLiveData<List<Person>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Person> teachers = personRepository.getTeachersSaved();

              mutableLiveData.postValue(teachers);
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
