/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 18:05 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: CourseRepository.java.
 * Last modified: 3/03/21 18:05.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.CourseService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.CourseDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SchoolDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import java.util.List;

public class CourseRepository {
  private static CourseRepository instance;
  private static CourseService courseService;
  private final SchoolDAO schoolDao;
  private final CourseDAO courseDao;

  private CourseRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    schoolDao = appDatabase.schoolDAO();
    courseDao = appDatabase.courseDAO();
    courseService = CourseService.getInstanceService(application);
  }

  public static CourseRepository getInstance(Application application) {
    if (instance == null) {
      instance = new CourseRepository(application);
    }

    return instance;
  }

  public void insert(Course newCourse) {
    if (newCourse != null) {
      School school = schoolDao.getSchool(newCourse.getSchool());

      if (school != null) {
        List<Course> courses = courseDao.getCourses(school.getId());

        if (courses != null && !courses.contains(newCourse)) {
          courseDao.insert(newCourse);
        } else {
          courseDao.update(newCourse);
        }
      }
    }
  }

  public Course getCourse(int course_id) throws ApiException {
    return courseService.getCourse(course_id);
  }

  public Course getCourseSaved(int course_id) {
    return courseDao.getCourseById(course_id);
  }
}
