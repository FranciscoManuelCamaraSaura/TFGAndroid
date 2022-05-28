/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 14/04/21 16:49 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: PreLogin.java.
 * Last modified: 14/04/21 16:49.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import com.tfgandroid.schoolmanager.databinding.PreLoginFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.PreLoginDirections.ActionPreLoginToLoginFragment;
import com.tfgandroid.schoolmanager.tfg.viewmodel.PreLoginViewModel;
import java.util.ArrayList;
import java.util.List;

public class PreLogin extends Fragment implements OnClickListener, OnItemSelectedListener {
  private static final String PROVINCE = "PROVINCE";
  private static final String LOCATION = "LOCATION";
  private Button buttonNext;
  private List<School> schools;
  private School schoolChoose;
  private Spinner locationsSpinner;
  private Spinner provinceSpinner;
  private Spinner schoolsSpinner;

  public static PreLogin newInstance() {
    return new PreLogin();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    PreLoginFragmentBinding preLoginFragmentBinding =
        PreLoginFragmentBinding.inflate(inflater, container, false);

    View view = preLoginFragmentBinding.getRoot();

    buttonNext = preLoginFragmentBinding.buttonNext;
    locationsSpinner = preLoginFragmentBinding.locationsSpinner;
    provinceSpinner = preLoginFragmentBinding.provinceSpinner;
    schoolsSpinner = preLoginFragmentBinding.schoolsSpinner;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    PreLoginViewModel preLoginViewModel =
        new ViewModelProvider(requireActivity()).get(PreLoginViewModel.class);

    preLoginViewModel
        .getSchools()
        .observe(
            getViewLifecycleOwner(),
            schools -> {
              if (schools != null) {
                setSchools(schools);
                String chosenProvince = loadProvinces(view.getContext());

                if (!chosenProvince.isEmpty()) {
                  String chosenLocation = loadLocations(view.getContext(), chosenProvince);

                  if (!chosenLocation.isEmpty()) {
                    schoolChoose = loadSchools(view.getContext(), chosenLocation);
                  }
                }
              }
            });

    buttonNext.setOnClickListener(this);
  }

  public void setSchools(List<School> schools) {
    this.schools = schools;
  }

  private String loadProvinces(Context context) {
    List<String> provinces = getProvinces();
    ArrayAdapter<String> provinceAdapter =
        new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinces);

    provinceSpinner.setAdapter(provinceAdapter);
    provinceSpinner.setOnItemSelectedListener(this);

    return provinceSpinner.getSelectedItem().toString();
  }

  private List<String> getProvinces() {
    List<String> provinces = new ArrayList<>();

    provinces.add(getResources().getString(R.string.login_provinces));

    for (School school : schools) {
      if (!provinces.contains(school.getProvince())) {
        provinces.add(school.getProvince());
      }
    }

    return provinces;
  }

  private String loadLocations(Context context, String chosenProvince) {
    List<String> locations = getLocations(chosenProvince);
    ArrayAdapter<String> locationsAdapter =
        new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, locations);

    locationsSpinner.setAdapter(locationsAdapter);
    locationsSpinner.setOnItemSelectedListener(this);

    return locationsSpinner.getSelectedItem().toString();
  }

  private List<String> getLocations(String chosenProvince) {
    List<String> locations = new ArrayList<>();

    locations.add(getResources().getString(R.string.login_locations));

    for (School school : schools) {
      if (chosenProvince.equals(school.getProvince())) {
        locations.add(school.getLocation());
      }
    }

    return locations;
  }

  private School loadSchools(Context context, String chosenLocation) {
    List<String> schoolsNames = getSchools(chosenLocation);

    ArrayAdapter<String> locationsAdapter =
        new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, schoolsNames);
    schoolsSpinner.setAdapter(locationsAdapter);
    schoolsSpinner.setOnItemSelectedListener(this);

    return schools.get(schoolsSpinner.getSelectedItemPosition());
  }

  private List<String> getSchools(String chosenLocation) {
    List<String> schoolsNames = new ArrayList<>();

    schoolsNames.add(getResources().getString(R.string.login_schools));

    for (School school : schools) {
      if (chosenLocation.equals(school.getLocation())) {
        schoolsNames.add(school.getName());
      }
    }

    return schoolsNames;
  }

  @Override
  public void onClick(View v) {
    ActionPreLoginToLoginFragment action =
        PreLoginDirections.actionPreLoginToLoginFragment(schoolChoose.getId());

    Navigation.findNavController(requireActivity(), R.id.navigationHostFragment).navigate(action);
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    parent.getItemAtPosition(position);

    if (parent.getId() == R.id.provinceSpinner) {
      restartSpinners(PROVINCE);
    } else if (parent.getId() == R.id.locationsSpinner) {
      restartSpinners(LOCATION);
    } else if (parent.getId() == R.id.schoolsSpinner) {
      buttonNext.setEnabled(
          provinceSpinner.getSelectedItemPosition() != 0
              && locationsSpinner.getSelectedItemPosition() != 0
              && schoolsSpinner.getSelectedItemPosition() != 0);
    }
  }

  private void restartSpinners(String spinner) {
    switch (spinner) {
      case PROVINCE:
        loadLocations(getContext(), provinceSpinner.getSelectedItem().toString());
        buttonNext.setEnabled(false);
        break;

      case LOCATION:
        loadSchools(getContext(), locationsSpinner.getSelectedItem().toString());
        buttonNext.setEnabled(false);
        break;
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {}
}
