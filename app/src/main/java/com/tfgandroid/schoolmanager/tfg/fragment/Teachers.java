/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/05/21 20:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Teachers.java.
 * Last modified: 5/05/21 20:41.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.tfg.adapter.TeacherAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.TeachersViewModel;

public class Teachers extends Fragment {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int teacherColumnCount = 1;

  public Teachers() {}

  public static Teachers newInstance(int columnCount) {
    Teachers fragment = new Teachers();
    Bundle args = new Bundle();

    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      teacherColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.teacher_list_fragment, container, false);

    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;

      if (teacherColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, teacherColumnCount));
      }

      TeachersViewModel teachersViewModel =
          new ViewModelProvider(requireActivity()).get(TeachersViewModel.class);

      teachersViewModel
          .getTeachers()
          .observe(
              getViewLifecycleOwner(),
              teachers -> recyclerView.setAdapter(new TeacherAdapter(teachers)));
    }

    return view;
  }
}
