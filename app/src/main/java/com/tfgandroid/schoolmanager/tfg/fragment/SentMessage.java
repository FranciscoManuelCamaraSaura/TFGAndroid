/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/05/21 19:41 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SentMessage.java.
 * Last modified: 22/05/21 19:41.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.databinding.SentMessageFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.SentMessageViewModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SentMessage extends Fragment {
  public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  public static final String LANGUAGE = "es";
  public static final String COUNTRY = "ES";
  private TextView messageReceiver;
  private TextView dataTime;
  private TextView messageMatter;
  private TextView messageContent;

  public static SentMessage newInstance() {
    return new SentMessage();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    SentMessageFragmentBinding sentMessageFragmentBinding =
        SentMessageFragmentBinding.inflate(inflater, container, false);

    View view = sentMessageFragmentBinding.getRoot();

    messageReceiver = sentMessageFragmentBinding.messageReceiver;
    dataTime = sentMessageFragmentBinding.dataTime;
    messageMatter = sentMessageFragmentBinding.messageMatter;
    messageContent = sentMessageFragmentBinding.messageContent;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));
    SentMessageViewModel sentMessageViewModel =
        new ViewModelProvider(requireActivity()).get(SentMessageViewModel.class);

    sentMessageViewModel
        .getPersons()
        .observe(
            getViewLifecycleOwner(),
            persons ->
                sentMessageViewModel
                    .getMessage(SentMessageArgs.fromBundle(getArguments()).getMessageId())
                    .observe(
                        getViewLifecycleOwner(),
                        message -> {
                          messageReceiver.setText(getName(persons, message.getReceiver()));
                          dataTime.setText(simpleDateFormat.format(message.getDate()));
                          messageMatter.setText(message.getMatter());
                          messageContent.setText(message.getText());

                          sentMessageViewModel.updateData(message);
                        }));
  }

  private String getName(List<Person> persons, String dni) {
    String name = "";

    for (Person person : persons) {
      if (dni.equals(person.getDni())) {
        name = person.getName();
      }
    }

    return name;
  }
}
