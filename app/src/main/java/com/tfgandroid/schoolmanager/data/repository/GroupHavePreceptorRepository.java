/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 8/03/21 20:41 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: GroupHavePreceptorRepository.java.
 * Last modified: 8/03/21 20:41.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupHavePreceptorDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.TeacherDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;
import com.tfgandroid.schoolmanager.data.access.database.entities.GroupHavePreceptor;
import com.tfgandroid.schoolmanager.data.access.database.entities.Teacher;

public class GroupHavePreceptorRepository {
  private static GroupHavePreceptorRepository instance;
  private final GroupDAO groupDao;
  private final TeacherDAO teacherDao;
  private final GroupHavePreceptorDAO groupHavePreceptorDao;

  private GroupHavePreceptorRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    groupDao = appDatabase.groupDAO();
    teacherDao = appDatabase.teacherDAO();
    groupHavePreceptorDao = appDatabase.groupHavePreceptorDAO();
  }

  public static GroupHavePreceptorRepository getInstance(Application application) {
    if (instance == null) {
      instance = new GroupHavePreceptorRepository(application);
    }

    return instance;
  }

  public void insert(GroupHavePreceptor newPreceptor) {
    if (newPreceptor != null) {
      Group group = groupDao.getGroup(newPreceptor.getCourse_id(), newPreceptor.getGroup_words());
      Teacher teacher = teacherDao.getTeacher(newPreceptor.getPreceptor());

      if (group != null && teacher != null) {
        String preceptorId =
            getPreceptor(newPreceptor.getCourse_id(), newPreceptor.getGroup_words());

        Teacher preceptor = null;

        if (preceptorId.isEmpty()) {
          preceptor = teacherDao.getTeacher(preceptorId);
        }

        if (preceptor == null
            || (teacher.getPerson().equals(preceptor.getPerson()) && !teacher.isPreceptor())) {
          groupHavePreceptorDao.insert(newPreceptor);
        } else {
          groupHavePreceptorDao.update(newPreceptor);
        }
      }
    }
  }

  public String getPreceptor(int course_id, String group_words) {
    return groupHavePreceptorDao.getGroupHavePreceptor(course_id, group_words);
  }
}
