/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/05/21 19:40 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Messages.java.
 * Last modified: 19/05/21 19:37.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.MessagesListFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.adapter.MessageAdapter;
import com.tfgandroid.schoolmanager.tfg.viewmodel.MessagesViewModel;
import java.util.List;
import java.util.Objects;

public class Messages extends Fragment implements OnClickListener, OnRefreshListener {
  private static final String ARG_COLUMN_COUNT = "column-count";
  private static final boolean SENT = false;
  private int messagesColumnCount = 1;
  private FloatingActionButton messagesFloatingActionButton;
  private MessagesViewModel messagesViewModel;
  private List<Person> persons;
  private RecyclerView recyclerView;
  private SwipeRefreshLayout swipeRefreshLayout;

  public Messages() {}

  public static Messages newInstance(int columnCount) {
    Messages fragment = new Messages();
    Bundle args = new Bundle();

    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      messagesColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    boolean isSent = MessagesArgs.fromBundle(getArguments()).getIsSendMessagesFragment();
    MessagesListFragmentBinding messagesListFragmentBinding =
        MessagesListFragmentBinding.inflate(inflater, container, false);
    View view = messagesListFragmentBinding.getRoot();

    Context context = view.getContext();
    recyclerView = messagesListFragmentBinding.list;

    if (messagesColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, messagesColumnCount));
    }

    messagesViewModel = new ViewModelProvider(requireActivity()).get(MessagesViewModel.class);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    messagesViewModel
        .getPersons()
        .observe(getViewLifecycleOwner(), persons -> this.persons = persons);

    messagesViewModel
        .getMessages(legalGuardian, isSent)
        .observe(
            getViewLifecycleOwner(),
            messages -> recyclerView.setAdapter(new MessageAdapter(messages, persons, isSent)));

    messagesFloatingActionButton = messagesListFragmentBinding.floatingActionButton;
    swipeRefreshLayout = messagesListFragmentBinding.swipeRefreshLayout;

    if (isSent) {
      messagesFloatingActionButton.setVisibility(View.VISIBLE);
      swipeRefreshLayout.setEnabled(false);
      Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar())
          .setTitle(R.string.message_sent_messages);
    } else {
      Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar())
          .setTitle(R.string.message_received_messages);
    }

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    messagesFloatingActionButton.setOnClickListener(this);
    swipeRefreshLayout.setOnRefreshListener(this);
  }

  @Override
  public void onClick(View view) {
    Navigation.findNavController(requireActivity(), R.id.navigationHostFragment)
        .navigate(R.id.action_messages_to_newMessage);
  }

  @Override
  public void onRefresh() {
    swipeRefreshLayout.setRefreshing(true);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    messagesViewModel
        .getMessages(legalGuardian, SENT)
        .observe(
            getViewLifecycleOwner(),
            messages -> {
              swipeRefreshLayout.setRefreshing(false);
              recyclerView.setAdapter(new MessageAdapter(messages, persons, SENT));
            });
  }
}
