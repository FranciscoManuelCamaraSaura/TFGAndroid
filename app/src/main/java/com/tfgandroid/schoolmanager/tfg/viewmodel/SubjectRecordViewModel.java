/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 31/05/21 16:56 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectRecordViewModel.java.
 * Last modified: 31/05/21 16:55.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
import com.tfgandroid.schoolmanager.data.repository.ExamRepository;
import com.tfgandroid.schoolmanager.data.repository.MakesRepository;
import java.util.List;

public class SubjectRecordViewModel extends AndroidViewModel {
  private final ExamRepository examRepository;
  private final MakesRepository makesRepository;
  private TypeError type;

  public SubjectRecordViewModel(Application application) {
    super(application);

    examRepository = ExamRepository.getInstance(application);
    makesRepository = MakesRepository.getInstance(application);
  }

  public MutableLiveData<List<String>> getNotes(String subjectCode) {
    MutableLiveData<List<String>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Exam> exams = examRepository.getSubjectExams(subjectCode);
              List<String> notes = makesRepository.getSubjectNotes(exams);

              mutableLiveData.postValue(notes);
            })
        .start();

    return mutableLiveData;
  }

  public TypeError getType() {
    return type;
  }

  public void setType(TypeError type) {
    this.type = type;
  }
}
