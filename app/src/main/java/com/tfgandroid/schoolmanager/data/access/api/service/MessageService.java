/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 24/03/21 19:51 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MessageService.java.
 * Last modified: 24/03/21 19:51.
 */

package com.tfgandroid.schoolmanager.data.access.api.service;

import android.app.Application;
import android.util.Log;
import com.tfgandroid.schoolmanager.data.access.api.ApiService;
import com.tfgandroid.schoolmanager.data.access.api.RetrofitClient;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeErrorConvert;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class MessageService {
  private static final String TAG = MessageService.class.getSimpleName();
  private static MessageService instanceService;
  private static ApiService apiService;

  public MessageService(Application application) {
    apiService = RetrofitClient.getApiService(application);
  }

  public static MessageService getInstanceService(Application application) {
    if (instanceService == null) {
      instanceService = new MessageService(application);
    }

    return instanceService;
  }

  public List<Message> getSentMessages(String sender) throws ApiException {
    Response<List<Message>> messagesResponse;

    try {
      messagesResponse = apiService.getSentMessagesCall(sender).execute();

      if (messagesResponse.isSuccessful()) {
        return messagesResponse.body();
      }

      throw TypeErrorConvert.parseError(messagesResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.SENT_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public List<Message> getReceivedMessages(String received) throws ApiException {
    Response<List<Message>> messagesResponse;

    try {
      messagesResponse = apiService.getReceivedMessagesCall(received).execute();

      if (messagesResponse.isSuccessful()) {
        return messagesResponse.body();
      }

      throw TypeErrorConvert.parseError(messagesResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.RECEIVED_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public Message sendMessage(Message newMessage) throws ApiException {
    Response<Message> messageResponse;

    try {
      messageResponse = apiService.sendMessageCall(newMessage).execute();

      if (messageResponse.isSuccessful()) {
        return messageResponse.body();
      }

      throw TypeErrorConvert.parseError(messageResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.NEW_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public Message readMessage(Message message) throws ApiException {
    Response<Message> messageResponse;

    try {
      messageResponse = apiService.setReadMessage(message.getId()).execute();

      if (messageResponse.isSuccessful()) {
        return messageResponse.body();
      }

      throw TypeErrorConvert.parseError(messageResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.NEW_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public Message replyMessage(Message message) throws ApiException {
    Response<Message> messageResponse;

    try {
      messageResponse = apiService.setReplyMessage(message.getId()).execute();

      if (messageResponse.isSuccessful()) {
        return messageResponse.body();
      }

      throw TypeErrorConvert.parseError(messageResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.NEW_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }

  public Message updateData(Message message) throws ApiException {
    Response<Message> messageResponse;

    try {
      messageResponse = apiService.updateData(message).execute();

      if (messageResponse.isSuccessful()) {
        return messageResponse.body();
      }

      throw TypeErrorConvert.parseError(messageResponse);
    } catch (IOException e) {
      ApiException apiException = new ApiException(TypeError.NEW_MESSAGES, e.getMessage());

      Log.e(TAG, apiException.getMessage(), e);

      throw apiException;
    }
  }
}
