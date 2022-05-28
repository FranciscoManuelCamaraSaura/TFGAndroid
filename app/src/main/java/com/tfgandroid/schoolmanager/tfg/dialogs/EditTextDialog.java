/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 28/6/21 19:32 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: EditTextDialog.java.
 * Last modified: 28/6/21 19:32.
 */

package com.tfgandroid.schoolmanager.tfg.dialogs;

import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.databinding.EditTextDialogBinding;
import java.util.Objects;

public class EditTextDialog extends DialogFragment {
  private EditTextDialogBinding editTextDialogBinding;
  private TextInputLayout textInputEditText;

  public AlertDialog createEditTextDialog(String message, String currentText) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = requireActivity().getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.edit_text_dialog, null));

    editTextDialogBinding = EditTextDialogBinding.inflate(getLayoutInflater());
    textInputEditText = editTextDialogBinding.editText;

    builder
        .setMessage(message)
        .setPositiveButton(
            R.string.button_literal_save,
            (dialog, id) -> {
              String newText =
                  Objects.requireNonNull(textInputEditText.getEditText())
                      .getText()
                      .toString()
                      .trim();
              if (!currentText.equals(newText)) {}
            })
        .setNegativeButton(R.string.button_literal_cancel, (dialog, id) -> {});

    return builder.create();
  }
}
