/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 7/06/21 17:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Alerts.java.
 * Last modified: 7/06/21 17:10.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.tfg.adapter.AlertsAdapter;

public class Alerts extends Fragment {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int alertColumnCount = 1;

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
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.alert_list_fragment, container, false);

    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;

      if (alertColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, alertColumnCount));
      }

      recyclerView.setAdapter(new AlertsAdapter(AlertItem.ITEMS));
    }

    return view;
  }
}
