/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 2/06/21 17:44 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DirectiveFile.java.
 * Last modified: 2/06/21 17:44.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.databinding.DirectiveFileFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.DirectiveFileDirections.ActionDirectiveFileToNewMessage;
import com.tfgandroid.schoolmanager.tfg.viewmodel.DirectiveFileViewModel;

public class DirectiveFile extends Fragment implements OnClickListener {
  private LinearLayout linearLayoutMessage;
  private Manager manager;
  private Person person;
  private TextView textViewMail;
  private TextView textViewName;
  private TextView textViewSurnames;
  private TextView textViewAdminTpe;

  public static DirectiveFile newInstance() {
    return new DirectiveFile();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    DirectiveFileFragmentBinding directiveFileFragmentBinding =
        DirectiveFileFragmentBinding.inflate(inflater, container, false);

    View view = directiveFileFragmentBinding.getRoot();

    textViewName = directiveFileFragmentBinding.textViewName;
    textViewSurnames = directiveFileFragmentBinding.textViewSurnames;
    textViewMail = directiveFileFragmentBinding.textViewMail;
    textViewAdminTpe = directiveFileFragmentBinding.textViewAdminTpe;
    linearLayoutMessage = directiveFileFragmentBinding.linearLayoutMessage;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    DirectiveFileViewModel directiveFileViewModel =
        new ViewModelProvider(requireActivity()).get(DirectiveFileViewModel.class);
    String managerDni = DirectiveFileArgs.fromBundle(getArguments()).getManager();

    directiveFileViewModel
        .getPerson(managerDni)
        .observe(
            getViewLifecycleOwner(),
            person -> {
              this.person = person;
              textViewName.setText(person.getName());
              textViewSurnames.setText(person.getSurnames());
              textViewMail.setText(person.getEmail());
            });

    directiveFileViewModel
        .getManager(managerDni)
        .observe(
            getViewLifecycleOwner(),
            manager -> {
              this.manager = manager;
              textViewAdminTpe.setText(manager.getType_admin().name());
            });

    linearLayoutMessage.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    ActionDirectiveFileToNewMessage action =
        DirectiveFileDirections.actionDirectiveFileToNewMessage();

    action.setSubject(manager.getType_admin().name());
    action.setReceiverName(person.getName());

    Navigation.findNavController(view).navigate(action);
  }
}
