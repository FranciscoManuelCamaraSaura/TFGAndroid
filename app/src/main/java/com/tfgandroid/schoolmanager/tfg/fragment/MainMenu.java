/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/04/21 17:50 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MainMenuFragment.java.
 * Last modified: 19/04/21 17:50.
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
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.tfg.adapter.MainMenuAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.MainMenuViewModel;
import com.tfgandroid.schoolmanager.utils.Utils;

public class MainMenu extends Fragment {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int menuColumnCount = 2;

  public MainMenu() {}

  public static MainMenu newInstance(int columnCount) {
    MainMenu fragment = new MainMenu();
    Bundle args = new Bundle();

    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      menuColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.main_menu_list_fragment, container, false);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);
    Login login = Preferences.getLogin(sharedPreferences);

    if (!Utils.neitherEmptyNorNull(legalGuardian)) {
      Navigation.findNavController(requireActivity(), R.id.navigationHostFragment)
          .navigate(R.id.toPreLogin);
    } else {
      MainMenuViewModel mainMenuViewModel =
          new ViewModelProvider(requireActivity()).get(MainMenuViewModel.class);

      mainMenuViewModel.getData(legalGuardian.getPerson(), login.getIdSchool());
    }

    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;

      if (menuColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, menuColumnCount));
      }

      recyclerView.setAdapter(new MainMenuAdapter(MainMenuItem.ITEMS, requireActivity()));
    }

    return view;
  }
}
