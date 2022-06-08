/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 7/06/21 17:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertsAdapter.java.
 * Last modified: 7/06/21 17:41.
 */

package com.tfgandroid.schoolmanager.tfg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import com.tfgandroid.schoolmanager.databinding.AlertItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.AlertsViewModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {
  private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  private static final String LANGUAGE = "es";
  private static final String COUNTRY = "ES";
  private final FragmentActivity fragmentActivity;
  private final List<Alert> alerts;

  public AlertAdapter(FragmentActivity fragmentActivity, List<Alert> alerts) {
    this.fragmentActivity = fragmentActivity;
    this.alerts = alerts;
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
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));
    AlertsViewModel alertsViewModel =
        new ViewModelProvider(fragmentActivity).get(AlertsViewModel.class);

    if (!alerts.get(position).isRead()) {
      holder.alert = alerts.get(position);
      holder.alertType.setText(alerts.get(position).getMatter());
      holder.dateAndHour.setText(simpleDateFormat.format(alerts.get(position).getSend_date()));

      holder.checkBox.setOnClickListener(
          view -> {
            holder.constraintLayout.setVisibility(View.GONE);
            alertsViewModel.setReadAlert(alerts.get(position));
          });
    } else {
        holder.checkBox.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return alerts.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View alertView;
    public Alert alert;
    public CheckBox checkBox;
    public ConstraintLayout constraintLayout;
    public TextView alertType;
    public TextView dateAndHour;

    public ViewHolder(View view) {
      super(view);

      AlertItemFragmentBinding alertItemFragmentBinding = AlertItemFragmentBinding.bind(view);

      alertView = view;
      alertType = alertItemFragmentBinding.alertType;
      checkBox = alertItemFragmentBinding.checkBox;
      constraintLayout = alertItemFragmentBinding.constraintLayout;
      dateAndHour = alertItemFragmentBinding.dateAndHour;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString() + " '" + alertType.getText() + "'";
    }
  }
}
