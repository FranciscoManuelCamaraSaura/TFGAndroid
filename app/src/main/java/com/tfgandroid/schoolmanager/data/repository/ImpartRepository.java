/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 10/03/21 17:25 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ImpartRepository.java.
 * Last modified: 10/03/21 17:25.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.ImpartService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.ImpartDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SubjectDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.TeacherDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;
import com.tfgandroid.schoolmanager.data.access.database.entities.Impart;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.access.database.entities.Teacher;
import java.util.List;

public class ImpartRepository {
  private static ImpartRepository instance;
  private static ImpartService impartService;
  private final GroupDAO groupDao;
  private final SubjectDAO subjectDao;
  private final TeacherDAO teacherDao;
  private final ImpartDAO impartDao;

  private ImpartRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    groupDao = appDatabase.groupDAO();
    subjectDao = appDatabase.subjectDAO();
    teacherDao = appDatabase.teacherDAO();
    impartDao = appDatabase.impartDAO();
    impartService = ImpartService.getInstanceService(application);
  }

  public static ImpartRepository getInstance(Application application) {
    if (instance == null) {
      instance = new ImpartRepository(application);
    }

    return instance;
  }

  public void insert(Impart newImpart) {
    Group group = groupDao.getGroup(newImpart.getCourse_id(), newImpart.getGroup_words());
    Subject subject = subjectDao.getSubject(newImpart.getSubject());
    Teacher teacher = teacherDao.getTeacher(newImpart.getTeacher());

    if (group != null && subject != null && teacher != null) {
      String teacherImpart =
          impartDao.getTeacher(group.getCourse_id(), group.getGroup_words(), subject.getCode());

      if (teacherImpart == null) {
        impartDao.insert(newImpart);
      } else {
        impartDao.update(newImpart);
      }
    }
  }

  public List<Impart> getImpart(int course_id, String group_words) throws ApiException {
    List<Impart> imparts = impartService.getImpart(course_id, group_words);

    for (Impart impart : imparts) {
      insert(impart);
    }

    return imparts;
  }

  public String getTeacher(String subject) {
    return impartDao.getTeacher(subject);
  }

  public List<String> getSubject(String teacher) {
    return impartDao.getSubjects(teacher);
  }
}
