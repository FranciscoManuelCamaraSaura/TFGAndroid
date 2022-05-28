/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 7/06/21 17:45 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AlertItem.java.
 * Last modified: 7/06/21 17:10.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertItem {
  public static final List<Alert> ITEMS = new ArrayList<>();

  public static final Map<String, Alert> ITEM_MAP = new HashMap<>();

  private static final int COUNT = 25;

  static {
    for (int i = 1; i <= COUNT; i++) {
      addItem(createAlert(i));
    }
  }

  private static void addItem(Alert item) {
    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  private static Alert createAlert(int position) {
    return new Alert(String.valueOf(position), "Item " + position);
  }

  public static class Alert {
    public final String id;
    public final String content;

    public Alert(String id, String content) {
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
