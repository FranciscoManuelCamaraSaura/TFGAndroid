/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/05/21 20:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TeacherAdapter.java.
 * Last modified: 5/05/21 20:41.
 */

package com.tfgandroid.schoolmanager.tfg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.databinding.TeacherItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.TeachersDirections;
import com.tfgandroid.schoolmanager.tfg.fragment.TeachersDirections.ActionTeachersToTeacherFile;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {
  private final List<Person> teachers;

  public TeacherAdapter(List<Person> teachers) {
    this.teachers = teachers;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.teacher_item_fragment, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.teacher = teachers.get(position);
    holder.textViewName.setText(teachers.get(position).getName());
    holder.textViewSurnames.setText(teachers.get(position).getSurnames());

    ActionTeachersToTeacherFile action =
        TeachersDirections.actionTeachersToTeacherFile(teachers.get(position).getDni());

    holder.constraintLayout.setOnClickListener(
        view -> Navigation.findNavController(view).navigate(action));
  }

  @Override
  public int getItemCount() {
    return teachers.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View teacherView;
    public ConstraintLayout constraintLayout;
    public Person teacher;
    public TextView textViewName;
    public TextView textViewSurnames;

    public ViewHolder(View view) {
      super(view);

      TeacherItemFragmentBinding teacherItemFragmentBinding = TeacherItemFragmentBinding.bind(view);

      teacherView = view;
      textViewName = teacherItemFragmentBinding.textViewName;
      textViewSurnames = teacherItemFragmentBinding.textViewSurnames;
      constraintLayout = teacherItemFragmentBinding.constraintLayout;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString()
          + " '"
          + textViewName.getText()
          + "'"
          + " '"
          + textViewSurnames.getText()
          + "'";
    }
  }
}
