/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/05/21 20:23 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectFile.java.
 * Last modified: 5/05/21 20:22.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.databinding.SubjectFileFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.SubjectFileDirections.ActionSubjectFileToTeacherFile;
import com.tfgandroid.schoolmanager.tfg.viewmodel.SubjectFileViewModel;

public class SubjectFile extends Fragment implements OnClickListener {
  private LinearLayout linearLayoutTeacher;
  private LinearLayout linearLayoutTeacherDescription;
  private Person teacher;
  private TextView textViewDescriptionContent;
  private TextView textViewName;
  private TextView textViewSurnames;

  public static SubjectFile newInstance() {
    return new SubjectFile();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    SubjectFileFragmentBinding subjectFileFragmentBinding =
        SubjectFileFragmentBinding.inflate(inflater, container, false);

    View view = subjectFileFragmentBinding.getRoot();

    textViewName = subjectFileFragmentBinding.textViewName;
    textViewSurnames = subjectFileFragmentBinding.textViewSurnames;
    textViewDescriptionContent = subjectFileFragmentBinding.textViewDescriptionContent;
    linearLayoutTeacher = subjectFileFragmentBinding.linearLayoutTeacher;
    linearLayoutTeacherDescription = subjectFileFragmentBinding.linearLayoutTeacherDescription;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SubjectFileViewModel subjectFileViewModel =
        new ViewModelProvider(requireActivity()).get(SubjectFileViewModel.class);

    String subjectCode = SubjectFileArgs.fromBundle(getArguments()).getSubject();

    subjectFileViewModel
        .getSubject(subjectCode)
        .observe(
            getViewLifecycleOwner(),
            subject -> textViewDescriptionContent.setText(subject.getDescription()));

    subjectFileViewModel
        .getTeacher(subjectCode)
        .observe(
            getViewLifecycleOwner(),
            teacher -> {
              this.teacher = teacher;
              textViewName.setText(teacher.getName());
              textViewSurnames.setText(teacher.getSurnames());
            });

    linearLayoutTeacher.setOnClickListener(this);
    linearLayoutTeacherDescription.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    ActionSubjectFileToTeacherFile action =
        SubjectFileDirections.actionSubjectFileToTeacherFile(teacher.getDni());

    Navigation.findNavController(view).navigate(action);
  }
}
