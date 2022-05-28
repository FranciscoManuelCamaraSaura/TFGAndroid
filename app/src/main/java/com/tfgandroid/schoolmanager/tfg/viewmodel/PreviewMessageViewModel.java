/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/05/21 18:07 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: PreviewMessageViewModel.java.
 * Last modified: 19/05/21 18:06.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.repository.MessageRepository;

public class PreviewMessageViewModel extends AndroidViewModel {
  private final MessageRepository messageRepository;
  private TypeError type;

  public PreviewMessageViewModel(Application application) {
    super(application);

    messageRepository = MessageRepository.getInstance(application);
  }

  public MutableLiveData<Boolean> sendMessage(Message message) {
    MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                messageRepository.sendMessage(message);
                mutableLiveData.postValue(true);
              } catch (ApiException e) {
                mutableLiveData.postValue(false);
                setType(e.getType());
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
