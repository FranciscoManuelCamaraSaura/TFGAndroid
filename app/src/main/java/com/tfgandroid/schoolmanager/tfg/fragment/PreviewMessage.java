/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/05/21 18:07 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: PreviewMessageFragment.java.
 * Last modified: 19/05/21 18:06.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.PreviewMessageFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.PreviewMessageViewModel;
import java.sql.Date;

public class PreviewMessage extends Fragment implements OnClickListener {
  public static final int ID = 0;
  public static final boolean INCLUSIVE = false;
  public static final boolean READ = false;
  public static final boolean REPLY = false;
  public static final String LANGUAGE = "es";
  public static final String COUNTRY = "ES";
  public static final String DATE_FORMAT = "dd/mm/yyyy hh:mm:ss";
  public static final String REQUEST_KEY = "PREVIEW_MESSAGE";
  public static final String RESULT_KEY = "RESULT";
  public static final String NAME_KEY = "NAME";
  public static final String SUBJECT_KEY = "SUBJECT";
  public static final String MESSAGE_TITLE_KEY = "MESSAGE_TITLE";
  public static final String MESSAGE_CONTENT_KEY = "MESSAGE_CONTENT";
  private Button previewMessageButtonBack;
  private Button previewMessageButtonConfirm;
  private int previousMessage;
  private PreviewMessageViewModel previewMessageViewModel;
  private String receiverDNI;
  private TextView previewMessageTextViewName;
  private TextView previewMessageTextViewSubject;
  private TextView previewMessageTextViewMessageTitle;
  private TextView previewMessageTextViewMessageContent;

  public static PreviewMessage newInstance() {
    return new PreviewMessage();
  }

  public static Bundle onButtonBackIsPress(
      TextView name, TextView subject, TextView messageTitle, TextView messageContent) {
    Bundle result = new Bundle();

    result.putString(NAME_KEY, name.getText().toString());
    result.putString(SUBJECT_KEY, subject.getText().toString());
    result.putString(MESSAGE_TITLE_KEY, messageTitle.getText().toString());
    result.putString(MESSAGE_CONTENT_KEY, messageContent.getText().toString());

    return result;
  }

  public Bundle onButtonConfirmIsPress() {
    Bundle result = new Bundle();

    result.putBoolean(RESULT_KEY, true);

    return result;
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    PreviewMessageFragmentBinding previewMessageFragmentBinding =
        PreviewMessageFragmentBinding.inflate(inflater, container, false);

    View view = previewMessageFragmentBinding.getRoot();

    previewMessageTextViewName = previewMessageFragmentBinding.textViewName;
    previewMessageTextViewSubject = previewMessageFragmentBinding.textViewSubject;
    previewMessageTextViewMessageTitle = previewMessageFragmentBinding.textViewMessageTitle;
    previewMessageTextViewMessageContent = previewMessageFragmentBinding.textViewMessageContent;
    previewMessageButtonBack = previewMessageFragmentBinding.buttonBack;
    previewMessageButtonConfirm = previewMessageFragmentBinding.buttonConfirm;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    previewMessageViewModel =
        new ViewModelProvider(requireActivity()).get(PreviewMessageViewModel.class);

    previewMessageTextViewName.setText(PreviewMessageArgs.fromBundle(getArguments()).getTeacher());
    receiverDNI = PreviewMessageArgs.fromBundle(getArguments()).getTeacherDNI();
    previewMessageTextViewSubject.setText(
        PreviewMessageArgs.fromBundle(getArguments()).getSubject());
    previewMessageTextViewMessageTitle.setText(
        PreviewMessageArgs.fromBundle(getArguments()).getMessageTitle());
    previewMessageTextViewMessageContent.setText(
        PreviewMessageArgs.fromBundle(getArguments()).getContentMessage());
    previousMessage = PreviewMessageArgs.fromBundle(getArguments()).getPreviousMessage();
    previewMessageButtonBack.setOnClickListener(this);
    previewMessageButtonConfirm.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.buttonConfirm) {
      SharedPreferences sharedPreferences =
          PreferenceManager.getDefaultSharedPreferences(requireContext());
      LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);
      Date date = new Date(System.currentTimeMillis());
      String matter = previewMessageTextViewMessageTitle.getText().toString().trim();
      String text = previewMessageTextViewMessageContent.getText().toString().trim();

      Message message =
          new Message(
              ID,
              date,
              matter,
              text,
              legalGuardian.getPerson(),
              receiverDNI,
              (previousMessage == 0) ? ID : previousMessage,
              READ,
              REPLY);

      previewMessageViewModel
          .sendMessage(message)
          .observe(
              getViewLifecycleOwner(),
              messageSent -> {
                if (messageSent) {
                  Bundle result = onButtonConfirmIsPress();

                  getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
                  NavHostFragment.findNavController(this).popBackStack(R.id.newMessage, INCLUSIVE);
                }
              });
    } else if (view.getId() == R.id.buttonBack) {
      requireActivity().onBackPressed();
    }
  }
}
