/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/03/21 19:55 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MakesRepository.java.
 * Last modified: 22/03/21 19:55.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.MakesService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.ExamDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.MakesDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.StudentDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
import com.tfgandroid.schoolmanager.data.access.database.entities.Makes;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MakesRepository {
  private static final int FIRST = 0;
  private static final int SECOND = 1;
  private static final int THIRD = 2;
  private static final int WRITTEN = 0;
  private static final int ORAL = 1;
  private static final int PRESENTATION = 2;
  private static final int EXHIBITION = 3;
  private static final int OPTIONAL_WORK = 4;
  private static final int HOMEWORK = 5;
  private static final String PATTERN = "#.00";
  private static MakesRepository instance;
  private static MakesService makesService;
  private final StudentDAO studentDao;
  private final ExamDAO examDao;
  private final MakesDAO makesDAO;

  private MakesRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    studentDao = appDatabase.studentDAO();
    examDao = appDatabase.examDAO();
    makesDAO = appDatabase.makesDAO();
    makesService = MakesService.getInstanceService(application);
  }

  public static MakesRepository getInstance(Application application) {
    if (instance == null) {
      instance = new MakesRepository(application);
    }

    return instance;
  }

  public void insert(Makes newMakes) {
    if (newMakes != null) {
      Student student = studentDao.getStudentById(newMakes.getStudent());
      Exam exam = examDao.getExam(newMakes.getExam());

      if (student != null && exam != null) {
        List<Integer> exams = makesDAO.getExam(student.getId());

        if (!exams.contains(exam.getId())) {
          makesDAO.insert(newMakes);
        } else {
          makesDAO.update(newMakes);
        }
      }
    }
  }

  public void getNotes(int student) throws ApiException {
    List<Makes> makes = makesService.getNotes(student);

    for (Makes make : makes) {
      insert(make);
    }
  }

  public List<String> getSubjectNotes(List<Exam> exams) {
    List<String> notes = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat(PATTERN);

    double firstTrimester = getNotesByTrimester(exams, FIRST);
    double secondTrimester = getNotesByTrimester(exams, SECOND);
    double thirdTrimester = getNotesByTrimester(exams, THIRD);
    double averageNote = (firstTrimester + secondTrimester + thirdTrimester) / 3;

    double jobNotes = getNotesByOtherQualifications(exams, OPTIONAL_WORK, HOMEWORK);
    double exhibitionNotes = getNotesByOtherQualifications(exams, PRESENTATION, EXHIBITION);
    double averageAdditionalNote = (jobNotes + exhibitionNotes) / 2;

    double finalNote = averageNote * 0.8 + averageAdditionalNote * 0.2;

    notes.add(decimalFormat.format(firstTrimester));
    notes.add(decimalFormat.format(secondTrimester));
    notes.add(decimalFormat.format(thirdTrimester));
    notes.add(decimalFormat.format(averageNote));
    notes.add(decimalFormat.format(jobNotes));
    notes.add(decimalFormat.format(exhibitionNotes));
    notes.add(decimalFormat.format(averageAdditionalNote));
    notes.add(decimalFormat.format(finalNote));

    return notes;
  }

  private Double getNotesByTrimester(List<Exam> exams, int trimester) {
    List<Double> notes = new ArrayList<>();

    for (Exam exam : exams) {
      if (exam.getEvaluation().getEvaluation() == trimester
          && (exam.getType_exam().getType_exam() == WRITTEN
              || exam.getType_exam().getType_exam() == ORAL)) {
        notes.add(makesDAO.getNote(exam.getId()));
      }
    }

    return getAverage(notes);
  }

  private Double getNotesByOtherQualifications(List<Exam> exams, int type_exam1, int type_exam2) {
    List<Double> notes = new ArrayList<>();

    for (Exam exam : exams) {
      if (exam.getType_exam().getType_exam() == type_exam1
          || exam.getType_exam().getType_exam() == type_exam2) {
        notes.add(makesDAO.getNote(exam.getId()));
      }
    }

    return getAverage(notes);
  }

  private Double getAverage(List<Double> notes) {
    Double average = 0.00;

    if (notes.size() > 0) {
      for (Double note : notes) {
        average += note;
      }

      average = average / notes.size();
    }

    return average;
  }
}
