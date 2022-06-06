/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/05/21 20:16 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DeveloperInformationFragment.java.
 * Last modified: 3/05/21 20:15.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.tfgandroid.schoolmanager.databinding.DeveloperInformationFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.DeveloperInformationViewModel;

public class DeveloperInformation extends Fragment {
  private DeveloperInformationViewModel mViewModel;

  public static DeveloperInformation newInstance() {
    return new DeveloperInformation();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    DeveloperInformationFragmentBinding developerInformationFragmentBinding =
        DeveloperInformationFragmentBinding.inflate(inflater, container, false);

      return developerInformationFragmentBinding.getRoot();
  }
}
