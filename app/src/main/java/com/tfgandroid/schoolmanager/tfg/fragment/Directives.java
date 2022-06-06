/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 2/06/21 15:47 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Directives.java.
 * Last modified: 2/06/21 15:45.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.DirectiveListFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.adapter.DirectiveAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.DirectivesViewModel;

public class Directives extends Fragment {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int managersColumnCount = 1;
  private DirectivesViewModel directivesViewModel;
  private TextView textViewSchoolName;
  private TextView textViewSchoolAddress;
  private TextView textViewSchoolLocation;
  private TextView textViewSchoolProvince;
  private TextView textViewSchoolPhone;
  private TextView textViewSchoolWeb;

  public Directives() {}

  public static Directives newInstance(int columnCount) {
    Directives fragment = new Directives();
    Bundle args = new Bundle();

    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      managersColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    DirectiveListFragmentBinding directiveListFragmentBinding =
        DirectiveListFragmentBinding.inflate(inflater, container, false);
    View view = directiveListFragmentBinding.getRoot();
    Context context = view.getContext();
    RecyclerView recyclerView = directiveListFragmentBinding.list;

    if (managersColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, managersColumnCount));
    }

    directivesViewModel = new ViewModelProvider(requireActivity()).get(DirectivesViewModel.class);

    directivesViewModel
        .getManagers()
        .observe(
            getViewLifecycleOwner(),
            managers -> recyclerView.setAdapter(new DirectiveAdapter(managers)));

    textViewSchoolName = directiveListFragmentBinding.textViewSchoolName;
    textViewSchoolAddress = directiveListFragmentBinding.textViewSchoolAddress;
    textViewSchoolLocation = directiveListFragmentBinding.textViewSchoolLocation;
    textViewSchoolProvince = directiveListFragmentBinding.textViewSchoolProvince;
    textViewSchoolPhone = directiveListFragmentBinding.textViewSchoolPhone;
    textViewSchoolWeb = directiveListFragmentBinding.textViewSchoolWeb;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    directivesViewModel
        .getSchool(legalGuardian.getPerson())
        .observe(
            getViewLifecycleOwner(),
            school -> {
              textViewSchoolName.setText(school.getName());
              textViewSchoolAddress.setText(school.getAddress());
              textViewSchoolLocation.setText(school.getLocation());
              textViewSchoolProvince.setText(school.getProvince());
              textViewSchoolPhone.setText(String.valueOf(school.getPhone()));
              textViewSchoolWeb.setText(school.getWeb_site());
            });
  }
}
