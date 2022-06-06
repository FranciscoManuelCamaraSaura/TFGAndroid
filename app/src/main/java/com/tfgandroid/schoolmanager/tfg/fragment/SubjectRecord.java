/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 31/05/21 16:55 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectRecord.java.
 * Last modified: 31/05/21 16:55.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.tfgandroid.schoolmanager.databinding.SubjectRecordFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.SubjectRecordViewModel;

public class SubjectRecord extends Fragment {
  private TextView textViewFirstTrimester;
  private TextView textViewSecondTrimester;
  private TextView textViewThirdTrimester;
  private TextView textViewAverageNote;
  private TextView textViewJobNotes;
  private TextView textViewExhibitionNotes;
  private TextView textViewAverageAdditionalNote;
  private TextView textViewFinalNote;

  public static SubjectRecord newInstance() {
    return new SubjectRecord();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    SubjectRecordFragmentBinding subjectRecordFragmentBinding =
        SubjectRecordFragmentBinding.inflate(inflater, container, false);

    View view = subjectRecordFragmentBinding.getRoot();

    textViewFirstTrimester = subjectRecordFragmentBinding.firstTrimester;
    textViewSecondTrimester = subjectRecordFragmentBinding.secondTrimester;
    textViewThirdTrimester = subjectRecordFragmentBinding.thirdTrimester;
    textViewAverageNote = subjectRecordFragmentBinding.averageNote;
    textViewJobNotes = subjectRecordFragmentBinding.jobNotes;
    textViewExhibitionNotes = subjectRecordFragmentBinding.exhibitionNotes;
    textViewAverageAdditionalNote = subjectRecordFragmentBinding.averageAdditionalNote;
    textViewFinalNote = subjectRecordFragmentBinding.finalNote;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SubjectRecordViewModel subjectRecordViewModel =
        new ViewModelProvider(requireActivity()).get(SubjectRecordViewModel.class);

    String subjectCode = SubjectFileArgs.fromBundle(getArguments()).getSubject();

    subjectRecordViewModel
        .getNotes(subjectCode)
        .observe(
            getViewLifecycleOwner(),
            notes -> {
              textViewFirstTrimester.setText(notes.get(0));
              textViewSecondTrimester.setText(notes.get(1));
              textViewThirdTrimester.setText(notes.get(2));
              textViewAverageNote.setText(notes.get(3));
              textViewJobNotes.setText(notes.get(4));
              textViewExhibitionNotes.setText(notes.get(5));
              textViewAverageAdditionalNote.setText(notes.get(6));
              textViewFinalNote.setText(notes.get(7));
            });
  }
}
