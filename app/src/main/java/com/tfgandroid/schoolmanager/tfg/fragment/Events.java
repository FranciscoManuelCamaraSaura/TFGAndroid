/*
 * Copyright (c) 2022. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 5/6/22 18:31 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EventFragment.java.
 * Last modified: 5/6/22 18:29.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.databinding.EventListFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.adapter.EventAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.EventsViewModel;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Events extends Fragment implements OnDateChangeListener {
  private static final int MIN_DAY = 1;
  private static final int MIN_MONTH = 8;
  private static final int MIN_YEAR = 2021;
  private static final int MAX_DAY = 30;
  private static final int MAX_MONTH = 5;
  private static final int MAX_YEAR = 2022;
  private static final int MINUTE = 0;
  private static final int HOUR_OF_DAY = 0;
  private static final int SECOND = 0;
  private static final String ARG_COLUMN_COUNT = "column-count";
  private static final String LANGUAGE = "es";
  private static final String COUNTRY = "ES";
  private static final String DATE_FORMAT = "dd/MM/yyyy";
  private int eventColumnCount = 1;
  private List<Event> events;
  private RecyclerView recyclerView;

  public Events() {}

  public static Events newInstance(int columnCount) {
    Events fragment = new Events();
    Bundle args = new Bundle();

    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      eventColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    EventListFragmentBinding eventListFragmentBinding =
        EventListFragmentBinding.inflate(inflater, container, false);
    View view = eventListFragmentBinding.getRoot();
    Context context = view.getContext();

    recyclerView = eventListFragmentBinding.list;

    if (eventColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, eventColumnCount));
    }

    EventsViewModel eventsViewModel =
        new ViewModelProvider(requireActivity()).get(EventsViewModel.class);

    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));
    String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));

    eventsViewModel
        .getEvents()
        .observe(
            getViewLifecycleOwner(),
            events -> {
              this.events = events;
              recyclerView.setAdapter(new EventAdapter(setEvents(date, events)));
            });

    CalendarView calendar = eventListFragmentBinding.calendarView;

    initCalendar(calendar);
    calendar.setOnDateChangeListener(this);

    return view;
  }

  @Override
  public void onSelectedDayChange(
      @NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));
    String date =
        simpleDateFormat.format(getDate(year, month, dayOfMonth, HOUR_OF_DAY, MINUTE, SECOND));

    recyclerView.setAdapter(new EventAdapter(setEvents(date, events)));
  }

  private List<Event> setEvents(String date, List<Event> events) {
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));
    List<Event> eventsOfDay = new ArrayList<>();

    for (Event event : events) {
      String eventDate = simpleDateFormat.format(event.getDate());

      if (date.equals(eventDate)) {
        eventsOfDay.add(event);
      }
    }

    return eventsOfDay;
  }

  private void initCalendar(CalendarView calendar) {
    calendar.setMinDate(getDate(MIN_YEAR, MIN_MONTH, MIN_DAY, HOUR_OF_DAY, MINUTE, SECOND));
    calendar.setMaxDate(getDate(MAX_YEAR, MAX_MONTH, MAX_DAY, HOUR_OF_DAY, MINUTE, SECOND));
  }

  private long getDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
    Calendar calendar = Calendar.getInstance();

    calendar.set(year, month, dayOfMonth, hourOfDay, minute, second);
    return calendar.getTimeInMillis();
  }
}
