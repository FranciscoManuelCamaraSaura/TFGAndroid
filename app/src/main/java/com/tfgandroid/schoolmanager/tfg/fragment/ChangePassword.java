/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/04/21 18:58 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ChangePassword.java.
 * Last modified: 29/04/21 18:57.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.PreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.ChangePasswordFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.ChangePasswordViewModel;
import com.tfgandroid.schoolmanager.utils.Utils;
import java.util.Objects;

public class ChangePassword extends Fragment implements OnClickListener {
  private Button changePasswordButtonChange;
  private ChangePasswordViewModel changePasswordViewModel;
  private TextInputLayout changePasswordEditTextInsertCurrentPassword;
  private TextInputLayout changePasswordEditTextInsertNewPassword;
  private TextInputLayout changePasswordEditTextRepeatNewPassword;

  public static ChangePassword newInstance() {
    return new ChangePassword();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    ChangePasswordFragmentBinding changePasswordFragmentBinding =
        ChangePasswordFragmentBinding.inflate(inflater, container, false);

    View view = changePasswordFragmentBinding.getRoot();

    changePasswordEditTextInsertCurrentPassword =
        changePasswordFragmentBinding.editTextInsertCurrentPassword;
    changePasswordEditTextInsertNewPassword =
        changePasswordFragmentBinding.editTextInsertNewPassword;
    changePasswordEditTextRepeatNewPassword =
        changePasswordFragmentBinding.editTextRepeatNewPassword;

    changePasswordButtonChange = changePasswordFragmentBinding.buttonChange;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    changePasswordViewModel =
        new ViewModelProvider(requireActivity()).get(ChangePasswordViewModel.class);

    changePasswordButtonChange.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    boolean currentPasswordError;
    boolean newPasswordError;
    boolean repeatNewPasswordError;
    String currentPassword = Utils.getStringValue(changePasswordEditTextInsertCurrentPassword);
    String newPassword = Utils.getStringValue(changePasswordEditTextInsertNewPassword);
    String repeatPassword = Utils.getStringValue(changePasswordEditTextRepeatNewPassword);

    currentPasswordError =
        Utils.isStringEmpty(
            currentPassword,
            changePasswordEditTextInsertCurrentPassword,
            getString(R.string.new_password_current_password_error));

    newPasswordError =
        Utils.isStringEmpty(
            newPassword,
            changePasswordEditTextInsertNewPassword,
            getString(R.string.new_password_enter_password_error));

    repeatNewPasswordError =
        Utils.isStringEmpty(
            repeatPassword,
            changePasswordEditTextRepeatNewPassword,
            getString(R.string.new_password_repeat_password_error));

    cheekInsertedValues(currentPasswordError, newPasswordError, repeatNewPasswordError);
  }

  private void cheekInsertedValues(
      boolean currentPasswordError, boolean newPasswordError, boolean repeatNewPasswordError) {
    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireActivity());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);
    Login login = Preferences.getLogin(sharedPreferences);

    if (!currentPasswordError && !newPasswordError && !repeatNewPasswordError) {
      if (checkCurrentPassword(
          legalGuardian.getPassword(), changePasswordEditTextInsertCurrentPassword)) {
        if (!checkPassword(
            changePasswordEditTextInsertCurrentPassword, changePasswordEditTextInsertNewPassword)) {
          if (checkPassword(
              changePasswordEditTextInsertNewPassword, changePasswordEditTextRepeatNewPassword)) {
            changePassword(legalGuardian, login);
          } else {
            changePasswordEditTextRepeatNewPassword.setError(
                getString(R.string.new_password_new_repeat_equals_error));
          }
        } else {
          changePasswordEditTextInsertNewPassword.setError(
              getString(R.string.new_password_current_new_equals_error));
        }
      } else {
        changePasswordEditTextInsertCurrentPassword.setError(
            getString(R.string.new_password_current_password_error));
      }
    }
  }

  public boolean checkCurrentPassword(String currentPassword, TextInputLayout insertedPassword) {
    return currentPassword.equals(
        Objects.requireNonNull(insertedPassword.getEditText()).getText().toString().trim());
  }

  public boolean checkPassword(TextInputLayout password1, TextInputLayout password2) {
    return Objects.requireNonNull(password1.getEditText())
        .getText()
        .toString()
        .trim()
        .equals(Objects.requireNonNull(password2.getEditText()).getText().toString().trim());
  }

  private void changePassword(LegalGuardian legalGuardian, Login login) {
    String newPassword =
        Objects.requireNonNull(changePasswordEditTextInsertNewPassword.getEditText())
            .getText()
            .toString()
            .trim();

    legalGuardian.setPassword(newPassword);
    login.setPassword(newPassword);

    changePasswordViewModel.setNewPassword(legalGuardian);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireActivity());
    Preferences.setLoggedLegalGuardian(sharedPreferences, legalGuardian);
    Preferences.setLogin(sharedPreferences, login);
  }
}
