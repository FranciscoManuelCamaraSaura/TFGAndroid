/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/04/21 20:19 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Help.java.
 * Last modified: 29/04/21 20:19.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.ContactUsDialogBinding;
import com.tfgandroid.schoolmanager.databinding.HelpFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.HelpViewModel;

public class Help extends Fragment implements OnClickListener {
  private AlertDialog dialog;
  private Button buttonTerms;
  private Button buttonContact;
  private Button buttonInformation;

  public static Help newInstance() {
    return new Help();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    HelpFragmentBinding helpFragmentBinding =
        HelpFragmentBinding.inflate(inflater, container, false);

    View view = helpFragmentBinding.getRoot();

    buttonTerms = helpFragmentBinding.buttonTerms;
    buttonContact = helpFragmentBinding.buttonContact;
    buttonInformation = helpFragmentBinding.buttonInformation;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    buttonTerms.setOnClickListener(this);
    buttonContact.setOnClickListener(this);
    buttonInformation.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.buttonTerms) {
    } else if (view.getId() == R.id.buttonContact) {
      createContactUsDialog();
    } else if (view.getId() == R.id.buttonInformation) {
      Navigation.findNavController(requireActivity(), R.id.navigationHostFragment)
          .navigate(R.id.action_help_to_developerInformation);
    } else if (view.getId() == R.id.buttonClose) {
      dialog.cancel();
    }
  }

  private void createContactUsDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    ContactUsDialogBinding contactUsDialogBinding =
        ContactUsDialogBinding.inflate(getLayoutInflater());

    builder.setView(contactUsDialogBinding.getRoot());

    TextView textEmail = contactUsDialogBinding.textEmail;
    TextView textPhone = contactUsDialogBinding.textPhone;
    Button buttonClose = contactUsDialogBinding.buttonClose;

    HelpViewModel helpViewModel = new ViewModelProvider(requireActivity()).get(HelpViewModel.class);
    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireActivity());
    Login login = Preferences.getLogin(sharedPreferences);

    helpViewModel
        .getSchool(login.getIdSchool())
        .observe(
            getViewLifecycleOwner(),
            school -> {
              textEmail.setText(school.getWeb_site());
              textPhone.setText(String.valueOf(school.getPhone()));
            });

    buttonClose.setOnClickListener(this);
    dialog = builder.create();
    dialog.show();
  }
}
