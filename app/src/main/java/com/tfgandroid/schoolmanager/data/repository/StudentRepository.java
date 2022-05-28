/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 8/03/21 19:03 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: StudentRepository.java.
 * Last modified: 8/03/21 19:03.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.StudentService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.LegalGuardianDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.StudentDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;

public class StudentRepository {
  private static StudentRepository instance;
  private static StudentService studentService;
  private final LegalGuardianDAO legalGuardianDao;
  private final GroupDAO groupDAO;
  private final StudentDAO studentDao;

  private StudentRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    legalGuardianDao = appDatabase.legalGuardianDAO();
    groupDAO = appDatabase.groupDAO();
    studentDao = appDatabase.studentDAO();
    studentService = StudentService.getInstanceService(application);
  }

  public static StudentRepository getInstance(Application application) {
    if (instance == null) {
      instance = new StudentRepository(application);
    }

    return instance;
  }

  public void insert(Student newStudent) {
    if (newStudent != null) {
      LegalGuardian legalGuardian =
          legalGuardianDao.getLegalGuardian(newStudent.getLegal_guardian());

      if (legalGuardian != null) {
        Student student = studentDao.getStudent(legalGuardian.getPerson());
        Group group = groupDAO.getGroup(newStudent.getCourse_id(), newStudent.getGroup_words());

        if (student == null && group != null) {
          studentDao.insert(newStudent);
        } else if (group != null) {
          studentDao.update(newStudent);
        }
      }
    }
  }

  public Student getStudent(String legalGuardian) throws ApiException {
    return studentService.getStudent(legalGuardian).get(0);
  }

  public Student getStudentSaved(String legalGuardian) {
    return studentDao.getStudent(legalGuardian);
  }
}
