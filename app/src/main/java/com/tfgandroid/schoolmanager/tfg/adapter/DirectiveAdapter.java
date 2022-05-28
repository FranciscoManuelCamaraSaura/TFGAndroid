/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 2/06/21 15:46 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DirectiveAdapter.java.
 * Last modified: 2/06/21 15:45.
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
import com.tfgandroid.schoolmanager.databinding.DirectiveItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.DirectivesDirections;
import com.tfgandroid.schoolmanager.tfg.fragment.DirectivesDirections.ActionDirectivesToDirectiveFile;
import java.util.List;

public class DirectiveAdapter extends RecyclerView.Adapter<DirectiveAdapter.ViewHolder> {
  private final List<Person> managers;

  public DirectiveAdapter(List<Person> managers) {
    this.managers = managers;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.directive_item_fragment, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.manager = managers.get(position);
    holder.textViewName.setText(managers.get(position).getName());
    holder.textViewSurnames.setText(managers.get(position).getSurnames());

    ActionDirectivesToDirectiveFile action =
        DirectivesDirections.actionDirectivesToDirectiveFile(managers.get(position).getDni());

    holder.constraintLayout.setOnClickListener(
        view -> Navigation.findNavController(view).navigate(action));
  }

  @Override
  public int getItemCount() {
    return managers.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View managerView;
    public ConstraintLayout constraintLayout;
    public Person manager;
    public TextView textViewName;
    public TextView textViewSurnames;

    public ViewHolder(View view) {
      super(view);

      DirectiveItemFragmentBinding directiveItemFragmentBinding =
          DirectiveItemFragmentBinding.bind(view);

      managerView = view;
      textViewName = directiveItemFragmentBinding.textViewName;
      textViewSurnames = directiveItemFragmentBinding.textViewSurnames;
      constraintLayout = directiveItemFragmentBinding.constraintLayout;
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
