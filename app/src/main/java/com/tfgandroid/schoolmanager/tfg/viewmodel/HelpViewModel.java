/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/04/21 20:19 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: HelpViewModel.java.
 * Last modified: 29/04/21 20:19.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import com.tfgandroid.schoolmanager.data.repository.SchoolRepository;

public class HelpViewModel extends AndroidViewModel {
  private final SchoolRepository schoolRepository;
  private TypeError type;

  public HelpViewModel(Application application) {
    super(application);

    schoolRepository = SchoolRepository.getInstance(application);
  }

  public MutableLiveData<School> getSchool(int schoolId) {
    MutableLiveData<School> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              School school = schoolRepository.getSchool(schoolId);

              mutableLiveData.postValue(school);
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
