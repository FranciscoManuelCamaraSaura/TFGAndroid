/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/05/21 16:09 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectRecyclerViewAdapter.java.
 * Last modified: 5/05/21 16:02.
 */

package com.tfgandroid.schoolmanager.tfg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.databinding.SubjectItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.SubjectsDirections;
import com.tfgandroid.schoolmanager.tfg.fragment.SubjectsDirections.ActionSubjectsToSubjectFile;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
  private final List<Subject> subjects;

  public SubjectAdapter(List<Subject> subjects) {
    this.subjects = subjects;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view =
        LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.subject_item_fragment, viewGroup, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.subject = subjects.get(position);
    holder.subjectButton.setText(subjects.get(position).getName());

    ActionSubjectsToSubjectFile action =
        SubjectsDirections.actionSubjectsToSubjectFile(subjects.get(position).getCode());

    holder.subjectButton.setOnClickListener(
        view -> Navigation.findNavController(view).navigate(action));
  }

  @Override
  public int getItemCount() {
    return subjects.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View subjectView;
    public Subject subject;
    public Button subjectButton;

    public ViewHolder(View view) {
      super(view);

      SubjectItemFragmentBinding subjectItemFragmentBinding = SubjectItemFragmentBinding.bind(view);

      subjectView = view;
      subjectButton = subjectItemFragmentBinding.buttonSubject;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString()
          + " '"
          + subjectButton.getId()
          + "' '"
          + subjectButton.getText()
          + "'";
    }
  }
}
