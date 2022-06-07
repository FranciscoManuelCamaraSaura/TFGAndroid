/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 6/6/22 19:45 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Event.java.
 * Last modified: 6/6/22 19:39.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.databinding.EventFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.EventViewModel;

public class Event extends Fragment {
  private EventViewModel eventViewModel;
  private LinearLayout linearLayoutExam;
  private TextView textViewEventName;
  private TextView textViewEventDescription;
  private TextView textViewEventDuration;
  private TextView textViewEventResponsible;
  private TextView textViewExamTitle;
  private TextView textViewExamSubject;
  private TextView textViewEventType;
  private TextView textViewEventEvaluation;
  private TextView textViewEventNote;

  public static Event newInstance() {
    return new Event();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    EventFragmentBinding eventFragmentBinding =
        EventFragmentBinding.inflate(inflater, container, false);

    View view = eventFragmentBinding.getRoot();

    textViewEventName = eventFragmentBinding.textViewEventName;
    textViewEventDescription = eventFragmentBinding.textViewEventDescription;
    textViewEventDuration = eventFragmentBinding.textViewEventDuration;
    textViewEventResponsible = eventFragmentBinding.textViewEventResponsible;
    textViewExamTitle = eventFragmentBinding.textViewExamTitle;
    linearLayoutExam = eventFragmentBinding.linearLayoutExam;
    textViewExamSubject = eventFragmentBinding.textViewExamSubject;
    textViewEventType = eventFragmentBinding.textViewEventType;
    textViewEventEvaluation = eventFragmentBinding.textViewEventEvaluation;
    textViewEventNote = eventFragmentBinding.textViewEventNote;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

    eventViewModel
        .getEvent(EventArgs.fromBundle(getArguments()).getEvent())
        .observe(
            getViewLifecycleOwner(),
            event -> {
              String duration =
                  event.getDuration()
                      + " "
                      + (event.getDuration() == 1
                          ? getResources().getString(R.string.event_hour)
                          : getResources().getString(R.string.event_hours));

              textViewEventName.setText(event.getName());
              textViewEventDescription.setText(event.getDescription());
              textViewEventDuration.setText(duration);

              eventViewModel
                  .getResponsible(event.getResponsable())
                  .observe(getViewLifecycleOwner(), name -> textViewEventResponsible.setText(name));

              eventViewModel
                  .getExam(event.getId())
                  .observe(
                      getViewLifecycleOwner(),
                      exam -> {
                        if (exam != null) {
                          eventViewModel
                              .getSubject(exam.getSubject())
                              .observe(
                                  getViewLifecycleOwner(),
                                  subject -> textViewExamSubject.setText(subject));

                          textViewEventType.setText(exam.getType_exam().toString());
                          textViewEventEvaluation.setText(exam.getEvaluation().toString());

                          eventViewModel
                              .getNote(exam.getId())
                              .observe(
                                  getViewLifecycleOwner(),
                                  note -> {
                                    if (note != null) {
                                      textViewEventNote.setText(String.valueOf(note));
                                    } else {
                                      textViewEventNote.setText(
                                          getResources().getString(R.string.makes_unassessed));
                                    }
                                  });
                        } else {
                          textViewExamTitle.setVisibility(View.GONE);
                          linearLayoutExam.setVisibility(View.GONE);
                        }
                      });
            });
  }
}
