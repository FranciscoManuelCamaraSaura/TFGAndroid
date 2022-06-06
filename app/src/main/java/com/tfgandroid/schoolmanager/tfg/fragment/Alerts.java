/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 7/06/21 17:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Alerts.java.
 * Last modified: 7/06/21 17:10.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.AlertListFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.adapter.AlertAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.AlertsViewModel;

public class Alerts extends Fragment implements OnRefreshListener {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int alertColumnCount = 1;
  private AlertsViewModel alertsViewModel;
  private RecyclerView recyclerView;
  private SwipeRefreshLayout swipeRefreshLayout;

  public Alerts() {}

  public static Alerts newInstance(int columnCount) {
    Alerts fragment = new Alerts();
    Bundle args = new Bundle();

    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      alertColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    AlertListFragmentBinding alertListFragmentBinding =
        AlertListFragmentBinding.inflate(inflater, container, false);
    View view = alertListFragmentBinding.getRoot();

    Context context = view.getContext();
    recyclerView = alertListFragmentBinding.list;

    if (alertColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, alertColumnCount));
    }

    alertsViewModel = new ViewModelProvider(requireActivity()).get(AlertsViewModel.class);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    alertsViewModel
        .getAlerts(legalGuardian)
        .observe(
            getViewLifecycleOwner(),
            alerts -> recyclerView.setAdapter(new AlertAdapter(requireActivity(), alerts)));

    swipeRefreshLayout = alertListFragmentBinding.swipeRefreshLayout;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    swipeRefreshLayout.setOnRefreshListener(this);
  }

  @Override
  public void onRefresh() {
    swipeRefreshLayout.setRefreshing(true);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    alertsViewModel
        .getAlerts(legalGuardian)
        .observe(
            getViewLifecycleOwner(),
            alerts -> {
              swipeRefreshLayout.setRefreshing(false);
              recyclerView.setAdapter(new AlertAdapter(requireActivity(), alerts));
            });
  }
}
