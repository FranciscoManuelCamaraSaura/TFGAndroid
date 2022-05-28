/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/04/21 18:57 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ChangePasswordViewModel.java.
 * Last modified: 29/04/21 18:57.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.repository.LegalGuardianRepository;

public class ChangePasswordViewModel extends AndroidViewModel {
  private final LegalGuardianRepository legalGuardianRepository;
  private TypeError type;

  public ChangePasswordViewModel(Application application) {
    super(application);

    legalGuardianRepository = LegalGuardianRepository.getInstance(application);
  }

  public void setNewPassword(LegalGuardian legalGuardian) {
    new Thread(
            () -> {
              try {
                legalGuardianRepository.setNewPassword(legalGuardian);
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
