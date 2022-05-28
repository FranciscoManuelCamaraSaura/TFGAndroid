/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 31/05/21 16:55 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectRecord.java.
 * Last modified: 31/05/21 16:55.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.tfg.viewmodel.SubjectRecordViewModel;

public class SubjectRecord extends Fragment {

  private SubjectRecordViewModel mViewModel;

  public static SubjectRecord newInstance() {
    return new SubjectRecord();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.subject_record_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(SubjectRecordViewModel.class);
    // TODO: Use the ViewModel
  }

}