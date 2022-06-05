/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:47 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: RecordsAdapter.java.
 * Last modified: 26/05/21 17:44.
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
import com.tfgandroid.schoolmanager.databinding.RecordItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.RecordsDirections;
import com.tfgandroid.schoolmanager.tfg.fragment.RecordsDirections.ActionRecordsToSubjectRecord;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
  private final List<Subject> subjects;

  public RecordAdapter(List<Subject> subjects) {
    this.subjects = subjects;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.record_item_fragment, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.record = subjects.get(position);
    holder.recordButton.setText(subjects.get(position).getName());

    ActionRecordsToSubjectRecord action =
        RecordsDirections.actionRecordsToSubjectRecord(subjects.get(position).getCode());

    holder.recordButton.setOnClickListener(
        view -> Navigation.findNavController(view).navigate(action));
  }

  @Override
  public int getItemCount() {
    return subjects.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View subjectView;
    public Subject record;
    public Button recordButton;

    public ViewHolder(View view) {
      super(view);

      RecordItemFragmentBinding recordItemFragmentBinding = RecordItemFragmentBinding.bind(view);

      subjectView = view;
      recordButton = recordItemFragmentBinding.subjectButton;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString() + " '" + recordButton.getText() + "'";
    }
  }
}
