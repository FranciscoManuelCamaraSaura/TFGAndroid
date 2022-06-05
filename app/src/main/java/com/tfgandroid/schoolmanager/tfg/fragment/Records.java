/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:47 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Records.java.
 * Last modified: 26/05/21 17:44.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.RecordListFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.adapter.RecordAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.RecordsViewModel;

public class Records extends Fragment {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int subjectColumnCount = 1;

  public Records() {}

  public static Records newInstance(int columnCount) {
    Records fragment = new Records();
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
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    RecordListFragmentBinding recordListFragmentBinding =
        RecordListFragmentBinding.inflate(inflater, container, false);
    View view = recordListFragmentBinding.getRoot();

    Context context = view.getContext();
    RecyclerView recyclerView = recordListFragmentBinding.list;

    if (subjectColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, subjectColumnCount));
    }

    RecordsViewModel recordsViewModel =
        new ViewModelProvider(requireActivity()).get(RecordsViewModel.class);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    recordsViewModel
        .getSubjects(legalGuardian)
        .observe(
            getViewLifecycleOwner(),
            subjects -> recyclerView.setAdapter(new RecordAdapter(subjects)));

    return view;
  }
}
