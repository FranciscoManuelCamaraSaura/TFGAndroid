/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 20/04/21 19:42 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MainMenuItem.java.
 * Last modified: 19/04/21 17:51.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenuItem {
  public static final List<MenuItem> ITEMS = new ArrayList<>();
  public static final Map<Integer, MenuItem> ITEM_MAP = new HashMap<>();
  private static final int COUNT = 6;

  static {
    for (int i = 1; i <= COUNT; i++) {
      addItem(createMenuItem(i));
    }
  }

  private static void addItem(MenuItem item) {
    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  private static MenuItem createMenuItem(int position) {
    String item = "";

    return new MenuItem(position, item);
  }

  public static class MenuItem {
    public final int id;
    public final String content;

    public MenuItem(int id, String content) {
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
