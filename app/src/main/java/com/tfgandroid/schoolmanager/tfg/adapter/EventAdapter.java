/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/6/22 18:30 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventAdapter.java.
 * Last modified: 5/6/22 18:29.
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
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.databinding.EventItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.EventsDirections;
import com.tfgandroid.schoolmanager.tfg.fragment.EventsDirections.ActionEventsToEvent;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
  private final List<Event> events;

  public EventAdapter(List<Event> events) {
    this.events = events;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.event_item_fragment, parent, false);

    return new EventAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.event = events.get(position);
    holder.eventName.setText(events.get(position).getName());
    holder.eventDescription.setText(events.get(position).getDescription());

    ActionEventsToEvent action = EventsDirections.actionEventsToEvent(events.get(position).getId());

    holder.constraintLayout.setOnClickListener(
        view -> Navigation.findNavController(view).navigate(action));
  }

  @Override
  public int getItemCount() {
    return events.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View eventView;
    public ConstraintLayout constraintLayout;
    public Event event;
    public TextView eventName;
    public TextView eventDescription;

    public ViewHolder(View view) {
      super(view);

      EventItemFragmentBinding eventItemFragmentBinding = EventItemFragmentBinding.bind(view);

      eventView = view;
      eventName = eventItemFragmentBinding.eventName;
      eventDescription = eventItemFragmentBinding.eventDescription;
      constraintLayout = eventItemFragmentBinding.constraintLayout;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString()
          + " '"
          + eventName.getText()
          + "'"
          + " '"
          + eventDescription.getText()
          + "'";
    }
  }
}
