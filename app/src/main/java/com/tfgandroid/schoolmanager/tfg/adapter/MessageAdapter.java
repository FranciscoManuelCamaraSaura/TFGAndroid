/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/05/21 19:40 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MessageAdapter.java.
 * Last modified: 19/05/21 19:37.
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
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.databinding.MessagesItemFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.fragment.MessagesDirections;
import com.tfgandroid.schoolmanager.tfg.fragment.MessagesDirections.ActionMessages2ToReceiverMessage;
import com.tfgandroid.schoolmanager.tfg.fragment.MessagesDirections.ActionMessages2ToSentMessage;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
  public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  public static final String LANGUAGE = "es";
  public static final String COUNTRY = "ES";
  private final boolean isSent;
  private final List<Message> messages;
  private final List<Person> persons;

  public MessageAdapter(List<Message> messages, List<Person> persons, boolean isSent) {
    this.messages = messages;
    this.persons = persons;
    this.isSent = isSent;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.messages_item_fragment, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));

    holder.message = messages.get(position);
    holder.messageContent.setText(messages.get(position).getText());

    if (isSent) {
      holder.personName.setText(getName(messages.get(position).getReceiver()));
    } else {
      holder.personName.setText(getName(messages.get(position).getSender()));
    }

    holder.messageHour.setText(simpleDateFormat.format(messages.get(position).getDate()));
    holder.constraintLayout.setOnClickListener(
        view -> {
          if (isSent) {
            ActionMessages2ToSentMessage action =
                MessagesDirections.actionMessages2ToSentMessage(messages.get(position).getId());
            Navigation.findNavController(view).navigate(action);
          } else {
            ActionMessages2ToReceiverMessage action =
                MessagesDirections.actionMessages2ToReceiverMessage(messages.get(position).getId());
            Navigation.findNavController(view).navigate(action);
          }
        });
  }

  @Override
  public int getItemCount() {
    return messages.size();
  }

  private String getName(String dni) {
    String name = "";

    for (Person person : persons) {
      if (dni.equals(person.getDni())) {
        name = person.getName();
      }
    }

    return name;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View messageView;
    public ConstraintLayout constraintLayout;
    public Message message;
    public TextView messageContent;
    public TextView personName;
    public TextView messageHour;

    public ViewHolder(View view) {
      super(view);

      MessagesItemFragmentBinding messagesListFragmentBinding =
          MessagesItemFragmentBinding.bind(view);

      messageView = view;
      messageContent = messagesListFragmentBinding.messageContent;
      personName = messagesListFragmentBinding.personName;
      messageHour = messagesListFragmentBinding.messageHour;
      constraintLayout = messagesListFragmentBinding.constraintLayout;
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString()
          + " '"
          // + messageContent.getText()
          + "'"
          + " '"
          // + personName.getText()
          + "'"
          + " '"
          // + messageHour.getText()
          + "'";
    }
  }
}
