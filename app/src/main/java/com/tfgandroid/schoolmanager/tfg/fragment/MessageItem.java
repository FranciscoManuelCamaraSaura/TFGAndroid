/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 19/05/21 19:41 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MessageItem.java.
 * Last modified: 19/05/21 19:40.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageItem {
  public static final List<MessagesItem> ITEMS = new ArrayList<>();
  public static final Map<String, MessagesItem> ITEM_MAP = new HashMap<>();
  private static final int COUNT = 25;

  static {
    for (int i = 1; i <= COUNT; i++) {
      addItem(createMessagesItem(i));
    }
  }

  private static void addItem(MessagesItem item) {
    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  private static MessagesItem createMessagesItem(int position) {
    return new MessagesItem(String.valueOf(position), "Item " + position);
  }

  public static class MessagesItem {
    public final String id;
    public final String content;

    public MessagesItem(String id, String content) {
      this.id = id;
      this.content = content;
    }

      @NonNull
    @Override
    public String toString() {
      return content;
    }
  }
}