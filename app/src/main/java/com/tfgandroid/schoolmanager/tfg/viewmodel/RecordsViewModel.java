/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 4/6/22 13:54 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: RecordsViewModel.java.
 * Last modified: 4/6/22 13:54.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.repository.SubjectRepository;
import java.util.List;

public class RecordsViewModel extends AndroidViewModel {
  private final SubjectRepository subjectRepository;
  private TypeError type;

  public RecordsViewModel(Application application) {
    super(application);

    subjectRepository = SubjectRepository.getInstance(application);
  }

  public MutableLiveData<List<Subject>> getSubjects(LegalGuardian legalGuardian) {
    MutableLiveData<List<Subject>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                List<Subject> subjects = subjectRepository.getSubjects(legalGuardian);

                mutableLiveData.postValue(subjects);
              } catch (ApiException e) {
                setType(e.getType());
                mutableLiveData.postValue(null);
              }
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
