/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 28/04/21 18:42 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ProfileFragment.java.
 * Last modified: 28/04/21 18:41.
 */

package com.tfgandroid.schoolmanager.tfg.fragment;

import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Patterns;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
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
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import com.tfgandroid.schoolmanager.data.preferences.Preferences;
import com.tfgandroid.schoolmanager.databinding.EditTextDialogBinding;
import com.tfgandroid.schoolmanager.databinding.ProfileFragmentBinding;
import com.tfgandroid.schoolmanager.tfg.viewmodel.ProfileViewModel;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class Profile extends Fragment implements OnClickListener {
  public static final String DATE_FORMAT = "dd-MM-yyyy";
  public static final String LANGUAGE = "es";
  public static final String COUNTRY = "ES";
  public static final Pattern LANDLINE_FORMAT = Pattern.compile("^(\\+34|0034|34)?[89]\\d{8}$");
  public static final Pattern MOBILE_PHONE_FORMAT = Pattern.compile("^(\\+34|0034|34)?[67]\\d{8}$");
  public static final Pattern EMAIL_FORMAT = Patterns.EMAIL_ADDRESS;
  private Button changePassword;
  private Button buttonSaveChanges;
  private LinearLayout profileLinearLayoutAddress;
  private LinearLayout profileLinearLayoutLocation;
  private LinearLayout profileLinearLayoutProvince;
  private LinearLayout profileLinearLayoutPhone;
  private LinearLayout profileLinearLayoutEmail;
  private Person person;
  private ProfileViewModel profileViewModel;
  private TextInputLayout textInputEditText;
  private TextView profileTextViewName;
  private TextView profileTextViewSurnames;
  private TextView profileTextViewBirthday;
  private TextView profileTextViewAddress;
  private TextView profileTextViewLocation;
  private TextView profileTextViewProvince;
  private TextView profileTextViewPhone;
  private TextView profileTextViewMail;
  private TextView profileTextViewDegree;
  private TextView profileTextViewCourse;

  public static Profile newInstance() {
    return new Profile();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    ProfileFragmentBinding profileFragmentBinding =
        ProfileFragmentBinding.inflate(inflater, container, false);

    View view = profileFragmentBinding.getRoot();

    profileTextViewName = profileFragmentBinding.textViewName;
    profileTextViewSurnames = profileFragmentBinding.textViewSurnames;
    profileTextViewBirthday = profileFragmentBinding.textViewBirthday;
    profileLinearLayoutAddress = profileFragmentBinding.linearLayoutAddress;
    profileTextViewLocation = profileFragmentBinding.textViewLocation;
    profileLinearLayoutLocation = profileFragmentBinding.linearLayoutLocation;
    profileTextViewProvince = profileFragmentBinding.textViewProvince;
    profileLinearLayoutProvince = profileFragmentBinding.linearLayoutProvince;
    profileTextViewAddress = profileFragmentBinding.textViewAddress;
    profileLinearLayoutPhone = profileFragmentBinding.linearLayoutPhone;
    profileTextViewPhone = profileFragmentBinding.textViewPhone;
    profileLinearLayoutEmail = profileFragmentBinding.linearLayoutEmail;
    profileTextViewMail = profileFragmentBinding.textViewMail;
    profileTextViewDegree = profileFragmentBinding.textViewDegree;
    profileTextViewCourse = profileFragmentBinding.textViewCourse;
    buttonSaveChanges = profileFragmentBinding.buttonSaveChanges;
    changePassword = profileFragmentBinding.buttonChangePassword;

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

    SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(requireContext());
    LegalGuardian legalGuardian = Preferences.getLoggedLegalGuardian(sharedPreferences);

    getPersonData(legalGuardian);
    getStudentData(legalGuardian);

    profileLinearLayoutAddress.setOnClickListener(this);
    profileLinearLayoutLocation.setOnClickListener(this);
    profileLinearLayoutProvince.setOnClickListener(this);
    profileLinearLayoutPhone.setOnClickListener(this);
    profileLinearLayoutEmail.setOnClickListener(this);
    buttonSaveChanges.setOnClickListener(this);
    changePassword.setOnClickListener(this);
  }

  private void getPersonData(LegalGuardian legalGuardian) {
    profileViewModel
        .getPerson(legalGuardian)
        .observe(
            getViewLifecycleOwner(),
            person -> {
              this.person = person;
              profileTextViewAddress.setText(person.getAddress());
              profileTextViewLocation.setText(person.getLocation());
              profileTextViewProvince.setText(person.getProvince());
              profileTextViewPhone.setText(person.getPhone());
              profileTextViewMail.setText(person.getEmail());
            });
  }

  private void getStudentData(LegalGuardian legalGuardian) {
    profileViewModel
        .getStudent(legalGuardian)
        .observe(
            getViewLifecycleOwner(),
            student -> {
              SimpleDateFormat simpleDateFormat =
                  new SimpleDateFormat(DATE_FORMAT, new Locale(LANGUAGE, COUNTRY));

              profileTextViewName.setText(student.getName());
              profileTextViewSurnames.setText(student.getSurnames());
              profileTextViewBirthday.setText(simpleDateFormat.format(student.getBirthday()));

              getCourseData(student);
            });
  }

  private void getCourseData(Student student) {
    profileViewModel
        .getCourse(student.getCourse_id())
        .observe(
            getViewLifecycleOwner(),
            course -> {
              String courseAndGroupFormat = course.getNumber() + "º " + student.getGroup_words();

              profileTextViewCourse.setText(courseAndGroupFormat);
              profileTextViewDegree.setText(course.getDegree().name());
            });
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.linearLayoutAddress) {
      createEditTextDialog(
          getResources().getString(R.string.profile_new_address),
          profileTextViewAddress,
          profileTextViewAddress.getText().toString(),
          false,
          false);
    } else if (view.getId() == R.id.linearLayoutLocation) {
      createEditTextDialog(
          getResources().getString(R.string.profile_new_location),
          profileTextViewLocation,
          profileTextViewLocation.getText().toString(),
          false,
          false);
    } else if (view.getId() == R.id.linearLayoutProvince) {
      createEditTextDialog(
          getResources().getString(R.string.profile_new_province),
          profileTextViewProvince,
          profileTextViewProvince.getText().toString(),
          false,
          false);
    } else if (view.getId() == R.id.linearLayoutPhone) {
      createEditTextDialog(
          getResources().getString(R.string.profile_new_phone),
          profileTextViewPhone,
          profileTextViewPhone.getText().toString(),
          true,
          false);
    } else if (view.getId() == R.id.linearLayoutEmail) {
      createEditTextDialog(
          getResources().getString(R.string.profile_new_email),
          profileTextViewMail,
          profileTextViewMail.getText().toString(),
          false,
          true);
    } else if (view.getId() == R.id.buttonSaveChanges) {
      saveChanges();
    } else if (view.getId() == R.id.buttonChangePassword) {
      Navigation.findNavController(requireActivity(), R.id.navigationHostFragment)
          .navigate(R.id.action_profile_to_changePassword);
    }
  }

  public void createEditTextDialog(
      String message, TextView textView, String currentText, boolean isPhone, boolean isEmail) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    EditTextDialogBinding editTextDialogBinding =
        EditTextDialogBinding.inflate(getLayoutInflater());

    builder.setView(editTextDialogBinding.getRoot());

    textInputEditText = editTextDialogBinding.editText;
    Objects.requireNonNull(textInputEditText.getEditText()).setText(currentText);

    if (isPhone) {
      textInputEditText
          .getEditText()
          .setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    builder
        .setMessage(message)
        .setPositiveButton(R.string.button_literal_save, null)
        .setNegativeButton(R.string.button_literal_cancel, (dialog, id) -> dialog.cancel());

    AlertDialog dialog = builder.create();

    setPositiveButton(textView, currentText, isPhone, isEmail, dialog);

    dialog.show();
  }

  private void setPositiveButton(
      TextView textView, String currentText, boolean isPhone, boolean isEmail, AlertDialog dialog) {
    dialog.setOnShowListener(
        dialogInterface -> {
          Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

          button.setOnClickListener(
              view -> {
                String newText =
                    Objects.requireNonNull(textInputEditText.getEditText())
                        .getText()
                        .toString()
                        .trim();

                if (!currentText.equals(newText)) {
                  if (isPhone && validatePhone(newText)) {
                    textView.setText(newText);
                    buttonSaveChanges.setEnabled(true);
                    dialog.dismiss();
                  } else if (isPhone) {
                    textInputEditText.setError(
                        getResources().getString(R.string.profile_wrong_phone));
                  } else if (isEmail && validateEmail(newText)) {
                    textView.setText(newText);
                    buttonSaveChanges.setEnabled(true);
                    dialog.dismiss();
                  } else if (isEmail) {
                    textInputEditText.setError(
                        getResources().getString(R.string.profile_wrong_email));
                  } else {
                    textView.setText(newText);
                    buttonSaveChanges.setEnabled(true);
                    dialog.dismiss();
                  }
                } else {
                  dialog.cancel();
                }
              });
        });
  }

  private boolean validatePhone(String phone) {
    return LANDLINE_FORMAT.matcher(phone).matches() || MOBILE_PHONE_FORMAT.matcher(phone).matches();
  }

  private boolean validateEmail(String email) {
    return EMAIL_FORMAT.matcher(email).matches();
  }

  public void saveChanges() {
    person.setAddress(profileTextViewAddress.getText().toString());
    person.setLocation(profileTextViewLocation.getText().toString());
    person.setProvince(profileTextViewProvince.getText().toString());
    person.setPhone(profileTextViewPhone.getText().toString());
    person.setEmail(profileTextViewMail.getText().toString());

    profileViewModel.setPerson(person);
    buttonSaveChanges.setEnabled(false);
  }
}
