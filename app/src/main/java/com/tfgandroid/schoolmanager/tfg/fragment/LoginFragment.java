/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 15/04/21 18:00 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Login.java.
 * Last modified: 15/04/21 18:00.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.tfgandroid.schoolmanager.R;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.LoginFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.LoginViewModel;
import com.tfgandroid.schoolmanager.utils.Utils;
import java.util.Objects;

public class LoginFragment extends Fragment implements OnClickListener {
  private Button loginButton;
  private ConstraintLayout loginLoadLayout;
  private LoginViewModel loginViewModel;
  private TextInputLayout loginEditTextPersonName;
  private TextInputLayout loginEditTextPersonPassword;
  private int idSchool;

  public static LoginFragment newInstance() {
    return new LoginFragment();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    LoginFragmentBinding loginFragmentBinding =
        LoginFragmentBinding.inflate(inflater, container, false);

    View view = loginFragmentBinding.getRoot();

    loginEditTextPersonName = loginFragmentBinding.editTextPersonName;
    loginEditTextPersonPassword = loginFragmentBinding.editTextPassword;
    loginButton = loginFragmentBinding.buttonLogin;
    loginLoadLayout = loginFragmentBinding.loadLayout;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    idSchool = LoginFragmentArgs.fromBundle(getArguments()).getIdSchool();

    Objects.requireNonNull(loginEditTextPersonPassword.getEditText())
        .setOnEditorActionListener(
            (v, actionId, event) -> {
              if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginButton.performClick();
                return true;
              }

              return false;
            });

    loginButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    String user =
        Objects.requireNonNull(loginEditTextPersonName.getEditText()).getText().toString().trim();
    String password =
        Objects.requireNonNull(loginEditTextPersonPassword.getEditText())
            .getText()
            .toString()
            .trim();

    if (!Utils.isStringEmpty(
            user, loginEditTextPersonName, getString(R.string.login_error_username))
        && !Utils.isStringEmpty(
            password, loginEditTextPersonPassword, getString(R.string.login_error_password))) {
      loginButton.setEnabled(false);
      loginLoadLayout.setVisibility(View.VISIBLE);

      Login login = new Login(user, password, idSchool);
      makeLogin(login);
    }
  }

  private void makeLogin(Login login) {
    loginViewModel
        .legalGuardianLogin(login)
        .observe(
            getViewLifecycleOwner(),
            legalGuardian -> {
              loginEditTextPersonName.setError("");
              loginEditTextPersonPassword.setError("");

              if (!Utils.neitherEmptyNorNull(legalGuardian)) {
                TypeError type = loginViewModel.getType();

                switch (type) {
                  case USERNAME:
                    loginEditTextPersonName.setError(getString(R.string.login_wrong_username));
                    break;

                  case PASSWORD:
                    loginEditTextPersonPassword.setError(getString(R.string.login_wrong_password));
                    break;

                  default:
                    type.showToast(requireContext());
                }
              } else {
                SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(requireActivity());

                Preferences.setLoggedLegalGuardian(sharedPreferences, legalGuardian);
                Preferences.setLogin(sharedPreferences, login);

                Navigation.findNavController(requireActivity(), R.id.navigationHostFragment)
                    .navigate(R.id.toMainMenu);
              }

              loginButton.setEnabled(true);
              loginLoadLayout.setVisibility(View.GONE);
            });

    Utils.hideKeyboard(requireActivity());
  }
}
