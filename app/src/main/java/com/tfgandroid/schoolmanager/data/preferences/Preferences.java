/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 31/03/21 17:58 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Preferences.java.
 * Last modified: 31/03/21 17:57.
 */

package com.tfgandroid.schoolmanager.data.preferences;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;

public class Preferences {
  public static final String DEFAULT_VALUE = "";
  public static final String PREF_LEGAL_GUARDIAN = "PREF_LEGAL_GUARDIAN";
  public static final String PREF_LOGIN = "PREF_LOGIN";
  private static final String PREFS_NAME = "LEGAL_GUARDIAN_PREFERENCES";
  // private static final String PREF_LEGAL_GUARDIAN_TOKEN = "PREF_LEGAL_GUARDIAN_TOKEN";

  public static LegalGuardian getLoggedLegalGuardian(SharedPreferences sharedPreferences) {
    Gson gson = new Gson();
    String legalGuardianString = sharedPreferences.getString(PREF_LEGAL_GUARDIAN, DEFAULT_VALUE);

    return gson.fromJson(legalGuardianString, LegalGuardian.class);
  }

  public static void setLoggedLegalGuardian(
      SharedPreferences sharedPreferences, LegalGuardian legalGuardian) {
    Gson gson = new Gson();
    String legalGuardianString = gson.toJson(legalGuardian);

    sharedPreferences.edit().putString(PREF_LEGAL_GUARDIAN, legalGuardianString).apply();
  }

  public static Login getLogin(SharedPreferences sharedPreferences) {
    Gson gson = new Gson();
    String loginString = sharedPreferences.getString(PREF_LOGIN, DEFAULT_VALUE);

    return gson.fromJson(loginString, Login.class);
  }

  public static void setLogin(SharedPreferences sharedPreferences, Login login) {
    Gson gson = new Gson();
    String loginString = gson.toJson(login);

    sharedPreferences.edit().putString(PREF_LOGIN, loginString).apply();
  }

  public static void setLogout(SharedPreferences sharedPreferences) {
    sharedPreferences.edit().putString(PREF_LEGAL_GUARDIAN, null).apply();
    sharedPreferences.edit().putString(PREF_LOGIN, null).apply();
  }
}
