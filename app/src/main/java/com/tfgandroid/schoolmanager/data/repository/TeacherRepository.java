/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 8/03/21 20:02 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: TeacherRepository.java.
 * Last modified: 8/03/21 20:02.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.PersonDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.TeacherDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Teacher;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {
  private static TeacherRepository instance;
  private final PersonDAO personDao;
  private final TeacherDAO teacherDao;

  private TeacherRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    personDao = appDatabase.personDAO();
    teacherDao = appDatabase.teacherDAO();
  }

  public static TeacherRepository getInstance(Application application) {
    if (instance == null) {
      instance = new TeacherRepository(application);
    }

    return instance;
  }

  public void insert(Teacher newTeacher) {
    if (newTeacher != null) {
      Person person = personDao.getPerson(newTeacher.getPerson());

      if (person != null) {
        Teacher teacher = teacherDao.getTeacher(person.getDni());

        if (teacher == null) {
          teacherDao.insert(newTeacher);
        } else {
          teacherDao.update(newTeacher);
        }
      }
    }
  }

  public List<String> getTeachers() {
    List<String> person = new ArrayList<>();
    List<Teacher> teachers = teacherDao.getTeachers();

    for (Teacher teacher : teachers) {
      person.add(teacher.getPerson());
    }

    return person;
  }

  public void setTeachers(List<Person> teachers) {
    for (Person person : teachers) {
      Teacher teacher = new Teacher(person.getDni(), false);
      insert(teacher);
    }
  }
}
