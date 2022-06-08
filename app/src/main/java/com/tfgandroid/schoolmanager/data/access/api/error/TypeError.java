/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 29/03/21 19:31 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TypreError.java.
 * Last modified: 29/03/21 19:31.
 */

package com.tfgandroid.schoolmanager.data.access.api.error;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.annotations.SerializedName;
import com.tfgandroid.schoolmanager.R;

public enum TypeError {
  @SerializedName("io_exception_courses")
  COURSES(R.string.io_exception_courses),
  @SerializedName("io_exception_events")
  EVENTS(R.string.io_exception_events),
  @SerializedName("io_exception_exams")
  EXAMS(R.string.io_exception_exams),
  @SerializedName("io_exception_student_group")
  STUDENT_GROUP(R.string.io_exception_student_group),
  @SerializedName("io_exception_group")
  GROUP(R.string.io_exception_group),
  @SerializedName("io_exception_impart")
  IMPART(R.string.io_exception_impart),
  @SerializedName("io_exception_login")
  LOGIN(R.string.io_exception_login),
  @SerializedName("io_exception_update_legal_guardian")
  LEGAL_GUARDIAN(R.string.io_exception_update_legal_guardian),
  @SerializedName("io_exception_manager")
  MANAGER(R.string.io_exception_manager),
  @SerializedName("io_exception_managers")
  MANAGERS(R.string.io_exception_managers),
  @SerializedName("io_exception_sent_messages")
  SENT_MESSAGES(R.string.io_exception_sent_messages),
  @SerializedName("io_exception_received_messages")
  RECEIVED_MESSAGES(R.string.io_exception_received_messages),
  @SerializedName("io_exception_new_message_sent")
  NEW_MESSAGES(R.string.io_exception_new_message_sent),
  @SerializedName("io_exception_schools")
  SCHOOLS(R.string.io_exception_schools),
  @SerializedName("io_exception_school")
  SCHOOL(R.string.io_exception_school),
  @SerializedName("io_exception_student")
  STUDENT(R.string.io_exception_student),
  @SerializedName("io_exception_subjects")
  SUBJECT(R.string.io_exception_subjects),
  @SerializedName("io_exception_teacher")
  TEACHER(R.string.io_exception_teacher),
  @SerializedName("io_exception_teachers")
  TEACHERS(R.string.io_exception_teachers),

  @SerializedName("other")
  NETWORK(R.string.network_error),
  @SerializedName("user_name")
  USERNAME(R.string.user_error),
  @SerializedName("password")
  PASSWORD(R.string.password_error),
  @SerializedName("other")
  OTHER(R.string.other_exception);

  private static final String TAG = TypeError.class.getSimpleName();
  private final int typeError;

  TypeError(int typeError) {
    this.typeError = typeError;
  }

  public int getTypeError() {
    return typeError;
  }

  public void showToast(Context context) {
    Toast.makeText(context, context.getString(typeError), Toast.LENGTH_SHORT).show();
    Log.e(TAG, context.getString(typeError));
  }
}
