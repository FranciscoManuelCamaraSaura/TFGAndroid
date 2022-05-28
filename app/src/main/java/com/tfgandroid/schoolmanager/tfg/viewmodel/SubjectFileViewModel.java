/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/05/21 20:23 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectFileViewModel.java.
 * Last modified: 5/05/21 20:22.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.repository.SubjectRepository;

public class SubjectFileViewModel extends AndroidViewModel {
  private final SubjectRepository subjectRepository;

  public SubjectFileViewModel(Application application) {
    super(application);

    subjectRepository = SubjectRepository.getInstance(application);
  }

  public MutableLiveData<Subject> getSubject(String subjectCode) {
    MutableLiveData<Subject> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Subject subject = subjectRepository.getSubject(subjectCode);

              mutableLiveData.postValue(subject);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Person> getTeacher(String subjectCode) {
    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Person teacher = subjectRepository.getTeacher(subjectCode);

              mutableLiveData.postValue(teacher);
            })
        .start();

    return mutableLiveData;
  }
}
