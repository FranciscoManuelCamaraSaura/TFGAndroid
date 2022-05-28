/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 12/05/21 19:53 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MessagesFragment.java.
 * Last modified: 12/05/21 19:53.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.databinding.MessagesMenuFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.MessagesMenuDirections.ActionMessagesOptionsToMessages;
import com.tfgandroid.schoolmanager.tfg.viewmodel.MessagesViewModel;

public class MessagesMenu extends Fragment implements OnClickListener {
  private Button messagesOptionsButtonNewMessage;
  private Button messagesOptionsButtonSentMessage;
  private Button messagesOptionsButtonReceivedMessage;
  private Button messagesOptionsButtonDeleteMessage;
  private MessagesViewModel mViewModel;

  public static MessagesMenu newInstance() {
    return new MessagesMenu();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    MessagesMenuFragmentBinding messagesFragmentBinding =
        MessagesMenuFragmentBinding.inflate(inflater, container, false);

    View view = messagesFragmentBinding.getRoot();

    messagesOptionsButtonNewMessage = messagesFragmentBinding.buttonNewMessage;
    messagesOptionsButtonSentMessage = messagesFragmentBinding.buttonSentMessages;
    messagesOptionsButtonReceivedMessage = messagesFragmentBinding.buttonReceivedMessages;
    messagesOptionsButtonDeleteMessage = messagesFragmentBinding.buttonDeleteMessages;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mViewModel = new ViewModelProvider(requireActivity()).get(MessagesViewModel.class);

    messagesOptionsButtonNewMessage.setOnClickListener(this);
    messagesOptionsButtonSentMessage.setOnClickListener(this);
    messagesOptionsButtonReceivedMessage.setOnClickListener(this);
    // messagesOptionsButtonDeleteMessage.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    ActionMessagesOptionsToMessages action =
        MessagesMenuDirections.actionMessagesOptionsToMessages();

    if (view.getId() == R.id.buttonNewMessage) {
      Navigation.findNavController(requireActivity(), R.id.navigationHostFragment)
          .navigate(R.id.action_messagesOptions_to_newMessage);
    } else if (view.getId() == R.id.buttonSentMessages) {
      action.setIsSendMessagesFragment(true);
      Navigation.findNavController(requireActivity(), R.id.navigationHostFragment).navigate(action);
    } else if (view.getId() == R.id.buttonReceivedMessages) {
      action.setIsSendMessagesFragment(false);
      Navigation.findNavController(requireActivity(), R.id.navigationHostFragment).navigate(action);
    }
  }
}
