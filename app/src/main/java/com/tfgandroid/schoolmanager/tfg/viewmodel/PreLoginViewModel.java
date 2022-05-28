/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 14/04/21 16:49 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: PreLoginViewModel.java.
 * Last modified: 14/04/21 16:49.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import com.tfgandroid.schoolmanager.data.repository.SchoolRepository;
import java.util.List;

public class PreLoginViewModel extends AndroidViewModel {
  private final SchoolRepository schoolRepository;
  private TypeError type;

  public PreLoginViewModel(Application application) {
    super(application);

    schoolRepository = SchoolRepository.getInstance(application);
  }

  public MutableLiveData<List<School>> getSchools() {
    MutableLiveData<List<School>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                List<School> schools = schoolRepository.getAllSchools();

                mutableLiveData.postValue(schools);
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
