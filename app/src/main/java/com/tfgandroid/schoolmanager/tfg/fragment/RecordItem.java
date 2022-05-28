/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 31/05/21 16:55 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: RecordItem.java.
 * Last modified: 31/05/21 16:52.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordItem {
  public static final List<Record> ITEMS = new ArrayList<>();
  public static final Map<String, Record> ITEM_MAP = new HashMap<>();
  private static final int COUNT = 10;

  static {
    for (int i = 1; i <= COUNT; i++) {
      addItem(createRecordItem(i));
    }
  }

  private static void addItem(Record item) {
    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  private static Record createRecordItem(int position) {
    return new Record(String.valueOf(position), "Item " + position);
  }

  public static class Record {
    public final String id;
    public final String content;

    public Record(String id, String content) {
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
