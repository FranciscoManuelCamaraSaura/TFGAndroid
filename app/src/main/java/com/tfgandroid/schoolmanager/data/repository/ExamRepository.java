/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 17/03/21 19:46 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ExamRepository.java.
 * Last modified: 17/03/21 19:46.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.ExamService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.CourseDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.EventDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.ExamDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SubjectDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import java.util.List;

public class ExamRepository {
  private static ExamRepository instance;
  private static ExamService examService;
  private final CourseDAO courseDao;
  private final GroupDAO groupDao;
  private final EventDAO eventDao;
  private final SubjectDAO subjectDao;
  private final ExamDAO examDAO;

  public ExamRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    courseDao = appDatabase.courseDAO();
    groupDao = appDatabase.groupDAO();
    eventDao = appDatabase.eventDAO();
    subjectDao = appDatabase.subjectDAO();
    examDAO = appDatabase.examDAO();
    examService = ExamService.getInstanceService(application);
  }

  public static ExamRepository getInstance(Application application) {
    if (instance == null) {
      instance = new ExamRepository(application);
    }

    return instance;
  }

  public void insert(Exam newExam) {
    Group group = groupDao.getGroup(newExam.getCourse_id(), newExam.getGroup_words());
    Course course = courseDao.getCourseById(newExam.getCourse_id());
    Event event = eventDao.getEvent(newExam.getEvent(), course.getSchool());
    Subject subject = subjectDao.getSubject(newExam.getSubject());

    if (group != null && event != null && subject != null) {
      Exam exam = examDAO.getExam(newExam.getId());

      if (exam == null) {
        examDAO.insert(newExam);
      } else {
        examDAO.update(newExam);
      }
    }
  }

  public void getExams(int course, String group) throws ApiException {
    List<Exam> exams = examService.getExamsCall(course, group);

    for (Exam exam : exams) {
      insert(exam);
    }
  }

  public List<Exam> getSubjectExams(String subjectCode) {
    return examDAO.getSubjectExam(subjectCode);
  }
}
