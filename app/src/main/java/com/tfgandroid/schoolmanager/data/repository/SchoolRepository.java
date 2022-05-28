/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:44 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: SchoolRepository.java.
 * Last modified: 3/03/21 17:44.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.SchoolService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.SchoolDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import java.util.List;

public class SchoolRepository {
  private static SchoolRepository instance;
  private static SchoolService schoolService;
  private final SchoolDAO schoolDao;

  private SchoolRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    schoolDao = appDatabase.schoolDAO();
    schoolService = SchoolService.getInstanceService(application);
  }

  public static SchoolRepository getInstance(Application application) {
    if (instance == null) {
      instance = new SchoolRepository(application);
    }

    return instance;
  }

  public void insert(School newSchool) {
    School school = schoolDao.getSchool(newSchool.getId());

    if (school == null) {
      schoolDao.insert(newSchool);
    } else {
      schoolDao.update(newSchool);
    }
  }

  public School getSchool(int schoolId) {
    return schoolDao.getSchool(schoolId);
  }

  private List<School> getSchools() throws ApiException {
    List<School> schools = schoolService.getAllSchools();

    for (School school : schools) {
      insert(school);
    }

    return schools;
  }

  public List<School> getAllSchools() throws ApiException {
    List<School> schools = schoolDao.getAllSchools();

    if (schools.isEmpty()) {
      schools = getSchools();
    }

    return schools;
  }
}
