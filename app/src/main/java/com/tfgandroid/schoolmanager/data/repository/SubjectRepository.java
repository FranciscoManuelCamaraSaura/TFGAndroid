/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 10/03/21 17:13 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SubjectRepository.java.
 * Last modified: 10/03/21 17:12.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.SubjectService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.ImpartDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.PersonDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.StudentDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SubjectDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import java.util.List;

public class SubjectRepository {
  private static SubjectRepository instance;
  private static SubjectService subjectService;
  private final ImpartDAO impartDAO;
  private final PersonDAO personDao;
  private final StudentDAO studentDao;
  private final SubjectDAO subjectDao;

  private SubjectRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    impartDAO = appDatabase.impartDAO();
    personDao = appDatabase.personDAO();
    studentDao = appDatabase.studentDAO();
    subjectDao = appDatabase.subjectDAO();
    subjectService = SubjectService.getInstanceService(application);
  }

  public static SubjectRepository getInstance(Application application) {
    if (instance == null) {
      instance = new SubjectRepository(application);
    }

    return instance;
  }

  public void insert(Subject newSubject) {
    Subject subject = subjectDao.getSubject(newSubject.getCode());

    if (subject == null) {
      subjectDao.insert(newSubject);
    } else {
      subjectDao.update(newSubject);
    }
  }

  public void getSubjects(String codes) throws ApiException {
    List<Subject> subjects = subjectService.getSubjects(codes);

    for (Subject subject : subjects) {
      insert(subject);
    }
  }

  public List<Subject> getSubjects(LegalGuardian legalGuardian) throws ApiException {
    Student student = studentDao.getStudent(legalGuardian.getPerson());
    List<String> codes = impartDAO.getSubject(student.getCourse_id(), student.getGroup_words());
    List<Subject> subjects = subjectService.getSubjects(getSubjectsId(codes));

    for (Subject subject : subjects) {
      insert(subject);
    }

    return subjects;
  }

  public String getSubjectsId(List<String> subjects) {
    StringBuilder subjectsId = new StringBuilder();

    for (int i = 0; i < subjects.size(); i++) {
      subjectsId.append(subjects.get(i));

      if (i < subjects.size() - 1) {
        subjectsId.append(", ");
      }
    }

    return subjectsId.toString();
  }

  public Subject getSubject(String subjectCode) {
    return subjectDao.getSubject(subjectCode);
  }

  public List<Subject> getSubjects() {
    return subjectDao.getSubjects();
  }

  public Person getTeacher(String subjectCode) {
    String teacher = impartDAO.getTeacher(subjectCode);

    return personDao.getPerson(teacher);
  }

  public String getSubjectName(String code) {
    return subjectDao.getSubject(code).getName();
  }
}
