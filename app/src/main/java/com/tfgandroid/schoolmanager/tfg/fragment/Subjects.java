/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/05/21 16:09 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectFragment.java.
 * Last modified: 5/05/21 16:02.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.tfg.adapter.SubjectAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.SubjectsViewModel;

public class Subjects extends Fragment {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int subjectColumnCount = 2;

  public Subjects() {}

  public static Subjects newInstance(int columnCount) {
    Subjects fragment = new Subjects();
    Bundle args = new Bundle();

    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      subjectColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.subject_list_fragment, container, false);

    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;

      if (subjectColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, subjectColumnCount));
      }

      SubjectsViewModel subjectsViewModel =
          new ViewModelProvider(requireActivity()).get(SubjectsViewModel.class);

      SharedPreferences sharedPreferences =
          PreferenceManager.getDefaultSharedPreferences(requireContext());
      LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

      subjectsViewModel
          .getSubjects(legalGuardian)
          .observe(
              getViewLifecycleOwner(),
              subjects -> recyclerView.setAdapter(new SubjectAdapter(subjects)));
    }

    return view;
  }
}
