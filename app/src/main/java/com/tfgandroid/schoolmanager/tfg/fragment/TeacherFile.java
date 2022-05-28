/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 12/05/21 19:22 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TeacherFileFragment.java.
 * Last modified: 12/05/21 19:22.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.databinding.TeacherFileFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.TeacherFileDirections.ActionTeacherFileToNewMessage;
import com.tfgandroid.schoolmanager.tfg.fragment.TeacherFileDirections.ActionTeacherFileToSubjectFile;
import com.tfgandroid.schoolmanager.tfg.viewmodel.TeacherFileViewModel;
import java.util.List;

public class TeacherFile extends Fragment implements OnClickListener {
  private static final int LEFT = 0;
  private static final int TOP = 0;
  private static final int RIGHT = 0;
  private static final int BOTTOM = 16;
  private LinearLayout linearLayoutMessage;
  private LinearLayout linearLayoutSubjects;
  private List<Subject> subjects;
  private Person teacher;
  private TextView textViewMail;
  private TextView textViewName;
  private TextView textViewSurnames;

  public static TeacherFile newInstance() {
    return new TeacherFile();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    TeacherFileFragmentBinding teacherFileFragmentBinding =
        TeacherFileFragmentBinding.inflate(inflater, container, false);

    View view = teacherFileFragmentBinding.getRoot();

    textViewName = teacherFileFragmentBinding.textViewName;
    textViewSurnames = teacherFileFragmentBinding.textViewSurnames;
    textViewMail = teacherFileFragmentBinding.textViewMail;
    linearLayoutMessage = teacherFileFragmentBinding.linearLayoutMessage;
    linearLayoutSubjects = teacherFileFragmentBinding.linearLayoutSubjects;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    TeacherFileViewModel teacherFileViewModel =
        new ViewModelProvider(requireActivity()).get(TeacherFileViewModel.class);
    String person = TeacherFileArgs.fromBundle(getArguments()).getTeacher();

    teacherFileViewModel
        .getTeacher(person)
        .observe(
            getViewLifecycleOwner(),
            teacher -> {
              this.teacher = teacher;
              textViewName.setText(teacher.getName());
              textViewSurnames.setText(teacher.getSurnames());
              textViewMail.setText(teacher.getEmail());
            });

    teacherFileViewModel
        .getSubject(person)
        .observe(
            getViewLifecycleOwner(),
            subjects -> {
              this.subjects = subjects;
              int i = 0;

              for (Subject subject : subjects) {
                LayoutParams params =
                    new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                TextView textView = new TextView(getContext());

                textView.setLayoutParams(params);
                textView.setPadding(LEFT, TOP, RIGHT, BOTTOM);
                textView.setText(subject.getName());
                textView.setTextSize(18);
                textView.setClickable(true);
                textView.setTag(i);

                textView.setOnClickListener(this);

                linearLayoutSubjects.addView(textView);
                i++;
              }
            });

    linearLayoutMessage.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == linearLayoutMessage.getId()) {
      Subject subject = subjects.get(0);

      ActionTeacherFileToNewMessage action = TeacherFileDirections.actionTeacherFileToNewMessage();

      action.setSubject(subject.getName());
      action.setReceiverName(teacher.getName());

      Navigation.findNavController(view).navigate(action);
    } else {
      Subject subject = subjects.get((int) view.getTag());

      ActionTeacherFileToSubjectFile action =
          TeacherFileDirections.actionTeacherFileToSubjectFile(subject.getCode());

      Navigation.findNavController(view).navigate(action);
    }
  }
}
