/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:46 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ReceiverMessage.java.
 * Last modified: 26/05/21 16:17.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.databinding.ReceiverMessageFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.ReceiverMessageDirections.ActionReceiverMessageToNewMessage;
import com.tfgandroid.schoolmanager.tfg.viewmodel.ReceiverMessageViewModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReceiverMessage extends Fragment implements OnClickListener {
  public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  public static final String LANGUAGE = "es";
  public static final String COUNTRY = "ES";
  private Button buttonReply;
  private ReceiverMessageViewModel receiverMessageViewModel;
  private Subject subject;
  private TextView messageSender;
  private TextView dataTime;
  private TextView messageMatter;
  private TextView messageContent;

  public static ReceiverMessage newInstance() {
    return new ReceiverMessage();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    ReceiverMessageFragmentBinding receiverMessageFragmentBinding =
        ReceiverMessageFragmentBinding.inflate(inflater, container, false);

    View view = receiverMessageFragmentBinding.getRoot();

    messageSender = receiverMessageFragmentBinding.messageSender;
    dataTime = receiverMessageFragmentBinding.dataTime;
    messageMatter = receiverMessageFragmentBinding.messageMatter;
    messageContent = receiverMessageFragmentBinding.messageContent;
    buttonReply = receiverMessageFragmentBinding.buttonReply;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));

    receiverMessageViewModel =
        new ViewModelProvider(requireActivity()).get(ReceiverMessageViewModel.class);

    receiverMessageViewModel
        .getPersons()
        .observe(
            getViewLifecycleOwner(),
            persons ->
                receiverMessageViewModel
                    .getMessage(ReceiverMessageArgs.fromBundle(getArguments()).getMessageId())
                    .observe(
                        getViewLifecycleOwner(),
                        message -> {
                          messageSender.setText(getName(persons, message.getSender()));
                          dataTime.setText(simpleDateFormat.format(message.getDate()));
                          messageMatter.setText(message.getMatter());
                          messageContent.setText(message.getText());

                          receiverMessageViewModel
                              .getSubject(message.getSender())
                              .observe(getViewLifecycleOwner(), subject -> this.subject = subject);

                          if (!message.isRead()) {
                            receiverMessageViewModel.setReadMessage(message);
                          }

                          if (message.isReply()) {
                            buttonReply.setEnabled(false);
                          }
                        }));

    buttonReply.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    ActionReceiverMessageToNewMessage action =
        ReceiverMessageDirections.actionReceiverMessageToNewMessage();

    action.setSubject(subject.getName());
    action.setReceiverName(messageSender.getText().toString());
    action.setPreviousMessage(ReceiverMessageArgs.fromBundle(getArguments()).getMessageId());

    Navigation.findNavController(requireActivity(), R.id.navigationHostFragment).navigate(action);
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
