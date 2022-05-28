/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 26/05/21 17:47 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: RecordsAdapter.java.
 * Last modified: 26/05/21 17:44.
 */

package com.tfgandroid.schoolmanager.tfg.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.tfg.fragment.RecordItem.Record;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
  private final List<Record> mValues;

  public RecordAdapter(List<Record> items) {
    mValues = items;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.record_item_fragment, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = mValues.get(position);
    holder.mContentView.setText(mValues.get(position).content);
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mContentView;
    public Record mItem;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mContentView = (TextView) view.findViewById(R.id.content);
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString() + " '" + mContentView.getText() + "'";
    }
  }
}
