/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 2/06/21 17:43 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DirectiveItem.java.
 * Last modified: 2/06/21 17:41.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectiveItem {
  public static final List<Item> ITEMS = new ArrayList<>();
  public static final Map<String, Item> ITEM_MAP = new HashMap<>();

  private static final int COUNT = 5;

  static {
    for (int i = 1; i <= COUNT; i++) {
      addItem(createItem(i));
    }
  }

  private static void addItem(Item item) {
    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  private static Item createItem(int position) {
    return new Item(String.valueOf(position), "Item " + position);
  }

  public static class Item {
    public final String id;
    public final String content;

    public Item(String id, String content) {
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
