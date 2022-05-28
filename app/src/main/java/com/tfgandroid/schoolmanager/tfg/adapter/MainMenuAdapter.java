/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/04/21 17:59 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MainMenuItem.java.
 * Last modified: 19/04/21 17:51.
 */

package com.tfgandroid.schoolmanager.tfg.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.databinding.MainMenuItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.MainMenuItem.MenuItem;
import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder>
    implements OnClickListener {
  private final List<MenuItem> menuValues;
  private final Activity activity;

  public MainMenuAdapter(List<MenuItem> items, Activity activity) {
    menuValues = items;
    this.activity = activity;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view =
        LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.main_menu_item_fragment, viewGroup, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.menuItem = menuValues.get(position);
    holder.menuButton.setId(menuValues.get(position).id);

    switch (position) {
      case 0:
        holder.menuButton.setText(R.string.main_menu_subjects);
        break;
      case 1:
        holder.menuButton.setText(R.string.main_menu_teachers);
        break;
      case 2:
        holder.menuButton.setText(R.string.main_menu_timetable);
        break;
      case 3:
        holder.menuButton.setText(R.string.main_menu_messages);
        break;
      case 4:
        holder.menuButton.setText(R.string.main_menu_record);
        break;
      case 5:
        holder.menuButton.setText(R.string.main_menu_center_information);
        break;
    }

    holder.menuButton.setOnClickListener(this);
  }

  @Override
  public int getItemCount() {
    return menuValues.size();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case 1:
        Navigation.findNavController(activity, R.id.navigationHostFragment)
            .navigate(R.id.action_mainMenu_to_subjects);
        break;
      case 2:
        Navigation.findNavController(activity, R.id.navigationHostFragment)
            .navigate(R.id.action_mainMenu_to_teachers);
        break;
      case 3:
        Navigation.findNavController(activity, R.id.navigationHostFragment)
            .navigate(R.id.toMainMenu);
        break;
      case 4:
        Navigation.findNavController(activity, R.id.navigationHostFragment)
            .navigate(R.id.action_mainMenu_to_messages);
        break;
      case 5:
        Navigation.findNavController(activity, R.id.navigationHostFragment)
            .navigate(R.id.action_mainMenu_to_records);
        break;
      case 6:
        Navigation.findNavController(activity, R.id.navigationHostFragment)
            .navigate(R.id.action_mainMenu_to_directives);
        break;
    }
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View menuView;
    public MenuItem menuItem;
    public Button menuButton;

    public ViewHolder(@NonNull View view) {
      super(view);

      MainMenuItemFragmentBinding mainMenuItemFragmentBinding =
          MainMenuItemFragmentBinding.bind(view);

      menuView = view;
      menuButton = mainMenuItemFragmentBinding.mainMenuButton;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString() + " '" + menuButton.getId() + "' '" + menuButton.getText() + "'";
    }
  }
}
