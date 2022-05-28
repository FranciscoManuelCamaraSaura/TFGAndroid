/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 7/06/21 17:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertsAdapter.java.
 * Last modified: 7/06/21 17:41.
 */

package com.tfgandroid.schoolmanager.tfg.adapter;

import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.databinding.AlertItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.AlertItem.Alert;

import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.ViewHolder> {
  private final List<Alert> mValues;

  public AlertsAdapter(List<Alert> items) {
    mValues = items;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.alert_item_fragment, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = mValues.get(position);
    holder.alertType.setText(mValues.get(position).content);
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View alertView;
    public final TextView alertType;
    public final TextView dateAndHour;
    public final CheckBox checkBox;
    public Alert mItem;

    public ViewHolder(View view) {
      super(view);

      AlertItemFragmentBinding alertItemFragmentBinding = AlertItemFragmentBinding.bind(view);

      alertView = view;
      alertType = alertItemFragmentBinding.alertType;
      dateAndHour = alertItemFragmentBinding.dateAndHour;
      checkBox = alertItemFragmentBinding.checkBox;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString() + " '" + alertType.getText() + "'";
    }
  }
}
