/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 14/6/21 20:08 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Utils.java.
 * Last modified: 14/6/21 20:08.
 */

package com.tfgandroid.schoolmanager.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import java.util.List;
import java.util.Objects;

public class Utils {
  // Utils to Android behaviour
  public static void hideKeyboard(Activity activity) {
    InputMethodManager editTextInput =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    View currentFocus = activity.getCurrentFocus();

    if (currentFocus != null && editTextInput != null) {
      editTextInput.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
  }

  // Utils to Java behaviour
  public static boolean neitherEmptyNorNull(LegalGuardian LegalGuardian) {
    return LegalGuardian != null;
  }

  public static boolean neitherEmptyNorNull(List<String> list) {
    return list.size() > 0;
  }

  public static boolean isStringEmpty(Context context, String value, String error) {
      if (value.isEmpty()) {
          Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
          return true;
      } else {
          return false;
      }
  }

  public static boolean isStringEmpty(String value, TextInputLayout textInputLayout, String error) {
    if (value.isEmpty()) {
      textInputLayout.setError(error);

      return true;
    } else {
      textInputLayout.setError(null);

      return false;
    }
  }

  public static String getStringValue(TextInputLayout textInputLayout) {
    return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
  }

  public static String getStringErrorValue(TextInputLayout textInputLayout) {
    return Objects.requireNonNull(textInputLayout.getError()).toString().trim();
  }
}
