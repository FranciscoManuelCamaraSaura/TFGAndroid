/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/05/21 16:34 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: NewMessageFragment.java.
 * Last modified: 19/05/21 16:32.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.databinding.NewMessageFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.NewMessageDirections.ActionNewMessageToPreviewMessage;
import com.tfgandroid.schoolmanager.tfg.viewmodel.NewMessageViewModel;
import com.tfgandroid.schoolmanager.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewMessage extends Fragment implements OnClickListener, OnItemSelectedListener {
  public static final boolean INCLUSIVE = false;
  public static final String REQUEST_KEY = "PREVIEW_MESSAGE";
  public static final String RESULT_KEY = "RESULT";
  public static final String NAME_KEY = "NAME";
  public static final String SUBJECT_KEY = "SUBJECT";
  public static final String MESSAGE_TITLE_KEY = "MESSAGE_TITLE";
  public static final String MESSAGE_CONTENT_KEY = "MESSAGE_CONTENT";
  private Button newMessageButtonSend;
  private int previousMessage;
  private List<Person> persons;
  private List<Person> personsBySubject;
  private List<Subject> subjects;
  private NewMessageViewModel newMessageViewModel;
  private Spinner newMessageSubjectSpinner;
  private Spinner newMessageTeacherSpinner;
  private TextInputLayout newMessageEditTextMessageTitle;
  private TextInputLayout newMessageEditTextMessageContent;
  private String personName;
  private String subjectName;

  public static NewMessage newInstance() {
    return new NewMessage();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getParentFragmentManager()
        .setFragmentResultListener(
            REQUEST_KEY,
            this,
            (key, bundle) -> {
              if (bundle.getBoolean(RESULT_KEY)) {
                NavHostFragment.findNavController(this)
                    .popBackStack(R.id.messagesFragment, INCLUSIVE);
              } else {
                personName = bundle.getString(NAME_KEY);
                subjectName = bundle.getString(SUBJECT_KEY);
                String messageTitle = bundle.getString(MESSAGE_TITLE_KEY);
                String messageContent = bundle.getString(MESSAGE_CONTENT_KEY);

                Objects.requireNonNull(newMessageEditTextMessageTitle.getEditText())
                    .setText(messageTitle);
                Objects.requireNonNull(newMessageEditTextMessageContent.getEditText())
                    .setText(messageContent);
              }
            });
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    NewMessageFragmentBinding newMessageFragmentBinding =
        NewMessageFragmentBinding.inflate(inflater, container, false);

    View view = newMessageFragmentBinding.getRoot();

    newMessageSubjectSpinner = newMessageFragmentBinding.subjectSpinner;
    newMessageTeacherSpinner = newMessageFragmentBinding.teacherSpinner;
    newMessageEditTextMessageTitle = newMessageFragmentBinding.editTextMessageTitle;
    newMessageEditTextMessageContent = newMessageFragmentBinding.editTextMessageContent;
    newMessageButtonSend = newMessageFragmentBinding.buttonSend;

    Objects.requireNonNull(newMessageEditTextMessageTitle.getEditText()).setSelectAllOnFocus(true);
    Objects.requireNonNull(newMessageEditTextMessageContent.getEditText())
        .setSelectAllOnFocus(true);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    newMessageViewModel = new ViewModelProvider(requireActivity()).get(NewMessageViewModel.class);

    getArgsFromBundle();

    newMessageViewModel
        .getManagers()
        .observe(
            getViewLifecycleOwner(),
            managers ->
                newMessageViewModel
                    .getSubjects()
                    .observe(
                        getViewLifecycleOwner(),
                        subjects -> {
                          this.subjects = subjects;
                          loadSubjects(view.getContext(), subjects, managers);
                        }));

    newMessageViewModel
        .getPersons()
        .observe(getViewLifecycleOwner(), persons -> this.persons = persons);

    loadInitPersons(view.getContext());

    newMessageButtonSend.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    String messageTitle =
        Objects.requireNonNull(newMessageEditTextMessageTitle.getEditText())
            .getText()
            .toString()
            .trim();
    String messageContent =
        Objects.requireNonNull(newMessageEditTextMessageContent.getEditText())
            .getText()
            .toString()
            .trim();
    String subject = newMessageSubjectSpinner.getSelectedItem().toString();
    String teacher = newMessageTeacherSpinner.getSelectedItem().toString();

    if (validateNewMessageValues(messageTitle, messageContent)) {
      ActionNewMessageToPreviewMessage action =
          NewMessageDirections.actionNewMessageToPreviewMessage();
      String teacherDNI =
          personsBySubject.get(newMessageTeacherSpinner.getSelectedItemPosition() - 1).getDni();

      action.setSubject(subject);
      action.setTeacher(teacher);
      action.setTeacherDNI(teacherDNI);
      action.setMessageTitle(messageTitle);
      action.setContentMessage(messageContent);
      action.setPreviousMessage(previousMessage);

      Navigation.findNavController(requireActivity(), R.id.navigationHostFragment).navigate(action);
    }
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    parent.getItemAtPosition(position);

    if (parent.getId() == R.id.subjectSpinner) {
      if (position > 0 && position < 5) {
        newMessageViewModel
            .getManagers(parent.getItemAtPosition(position).toString())
            .observe(
                getViewLifecycleOwner(),
                managers -> {
                  personsBySubject = new ArrayList<>();

                  for (Manager manager : managers) {
                    for (Person person : this.persons) {
                      if (manager.getPerson().equals(person.getDni())) {
                        personsBySubject.add(person);
                      }
                    }
                  }

                  loadPersons(view.getContext());
                });
      } else if (position > 4) {
        Subject subject = subjects.get(position - 5);

        newMessageViewModel
            .getImpart(subject.getCode())
            .observe(
                getViewLifecycleOwner(),
                person -> {
                  personsBySubject = new ArrayList<>();

                  personsBySubject.add(person);
                  loadPersons(view.getContext());
                });
      }
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {}

  private void loadSubjects(Context context, List<Subject> subjects, List<Manager> managers) {
    List<String> subjectsNamesOrAdminType = new ArrayList<>();

    subjectsNamesOrAdminType.addAll(getAdminType(managers));
    subjectsNamesOrAdminType.addAll(getSubjects(subjects));

    ArrayAdapter<String> subjectsOrAdminTypeAdapter =
        new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, subjectsNamesOrAdminType);

    newMessageSubjectSpinner.setAdapter(subjectsOrAdminTypeAdapter);
    newMessageSubjectSpinner.setSelection(getSubjectPosition(subjects, managers));
    newMessageSubjectSpinner.setOnItemSelectedListener(this);
  }

  private List<String> getAdminType(List<Manager> managers) {
    List<String> adminType = new ArrayList<>();

    adminType.add(getResources().getString(R.string.new_message_select_subject_type));

    for (Manager manager : managers) {
      if (!adminType.contains(manager.getType_admin().name())) {
        adminType.add(manager.getType_admin().name());
      }
    }

    return adminType;
  }

  private List<String> getSubjects(List<Subject> subjects) {
    List<String> subjectsNames = new ArrayList<>();

    for (Subject subject : subjects) {
      subjectsNames.add(subject.getName());
    }

    return subjectsNames;
  }

  private int getSubjectPosition(List<Subject> subjects, List<Manager> managers) {
    int i, j;
    int position = 0;

    ArrayList<String> adminTypes = new ArrayList<>();

    if (subjectName != null) {
      for (i = 0; i < managers.size(); i++) {
        if (subjectName.equals(managers.get(i).getType_admin().name())
            && !adminTypes.contains(subjectName)) {
          position = i + 1;
        }

        adminTypes.add(managers.get(i).getType_admin().name());
      }

      for (j = 0; j < subjects.size(); j++) {
        if (subjectName.equals(subjects.get(j).getName())) {
          position = i + j;
        }
      }
    }

    return position;
  }

  private void loadInitPersons(Context context) {
    List<String> personsNames = new ArrayList<>();

    personsNames.add(getResources().getString(R.string.new_message_select_person));

    ArrayAdapter<String> personsAdapter =
        new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, personsNames);

    newMessageTeacherSpinner.setAdapter(personsAdapter);
    newMessageTeacherSpinner.setOnItemSelectedListener(this);
  }

  private void loadPersons(Context context) {
    int position = getPersonPosition();
    List<String> personsNames = getPersons();
    ArrayAdapter<String> personsAdapter =
        new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, personsNames);

    newMessageTeacherSpinner.setAdapter(personsAdapter);

    if (position != 0) {
      newMessageTeacherSpinner.setSelection(position);
    }

    newMessageTeacherSpinner.setOnItemSelectedListener(this);
  }

  private List<String> getPersons() {
    List<String> personsNames = new ArrayList<>();

    personsNames.add(getResources().getString(R.string.new_message_select_person));

    if (!persons.isEmpty()) {
      for (Person person : personsBySubject) {
        personsNames.add(person.getName());
      }
    }

    return personsNames;
  }

  private int getPersonPosition() {
    int position = 0;

    if (personName != null) {
      for (int i = 0; i < personsBySubject.size(); i++) {
        if (personName.equals(personsBySubject.get(i).getName())) {
          position = i + 1;
        }
      }
    }

    return position;
  }

  private boolean validateNewMessageValues(String messageTitle, String messageContent) {
    return !Utils.isStringEmpty(
            messageTitle,
            newMessageEditTextMessageTitle,
            getString(R.string.new_message_message_title_error))
        && !Utils.isStringEmpty(
            messageContent,
            newMessageEditTextMessageContent,
            getString(R.string.new_message_content_message_error))
        && newMessageSubjectSpinner.getSelectedItemPosition() != 0
        && newMessageTeacherSpinner.getSelectedItemPosition() != 0;
  }

  private void getResultListener() {
    getParentFragmentManager()
        .setFragmentResultListener(
            REQUEST_KEY,
            getViewLifecycleOwner(),
            (key, bundle) -> {
              if (bundle.getBoolean(RESULT_KEY)) {
                getParentFragmentManager().popBackStack();
              } else {
                getBundleValues(bundle);
              }
            });
  }

  private void getBundleValues(Bundle bundle) {
    personName = bundle.getString(NAME_KEY);
    subjectName = bundle.getString(SUBJECT_KEY);
    String messageTitle = bundle.getString(MESSAGE_TITLE_KEY);
    String messageContent = bundle.getString(MESSAGE_CONTENT_KEY);

    Objects.requireNonNull(newMessageEditTextMessageTitle.getEditText()).setText(messageTitle);
    Objects.requireNonNull(newMessageEditTextMessageContent.getEditText()).setText(messageContent);
  }

  private void getArgsFromBundle() {
    subjectName = NewMessageArgs.fromBundle(getArguments()).getSubject();
    personName = NewMessageArgs.fromBundle(getArguments()).getReceiverName();
    previousMessage = NewMessageArgs.fromBundle(getArguments()).getPreviousMessage();
  }
}
