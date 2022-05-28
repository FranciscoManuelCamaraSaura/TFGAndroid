/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 8/03/21 19:50 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ManagerRepository.java.
 * Last modified: 8/03/21 19:50.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.ManagerService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.convert.AdminTypeConvert;
import com.tfgandroid.schoolmanager.data.access.database.dao.ManagerDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.PersonDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SchoolDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import com.tfgandroid.schoolmanager.data.enums.Admin;
import java.util.List;

public class ManagerRepository {
  private static ManagerRepository instance;
  private static ManagerService managerService;
  private final SchoolDAO schoolDao;
  private final PersonDAO personDao;
  private final ManagerDAO managerDao;

  private ManagerRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    schoolDao = appDatabase.schoolDAO();
    personDao = appDatabase.personDAO();
    managerDao = appDatabase.managerDAO();
    managerService = ManagerService.getInstanceService(application);
  }

  public static ManagerRepository getInstance(Application application) {
    if (instance == null) {
      instance = new ManagerRepository(application);
    }

    return instance;
  }

  public void insert(Manager newManager) {
    if (newManager != null) {
      School school = schoolDao.getSchool(newManager.getSchool());
      Person person = personDao.getPerson(newManager.getPerson());

      if (school != null && person != null) {
        Manager manager = managerDao.getManager(school.getId(), person.getDni());

        if (manager == null) {
          managerDao.insert(newManager);
        } else {
          managerDao.update(newManager);
        }
      }
    }
  }

  public Manager getManager(String person_id) {
    return managerDao.getManager(person_id);
  }

  public List<Manager> getManagers() {
    return managerDao.getManagers();
  }

  public void setManagers(List<Manager> managers) throws ApiException {
    for (Manager manager : managers) {
      insert(manager);
    }
  }

  public List<Manager> getManagers(String adminType) {
    Admin admin;

    switch (adminType) {
      case "DIRECTOR":
        admin = AdminTypeConvert.toAdmin(0);
        break;
      case "SUB_DIRECTOR":
        admin = AdminTypeConvert.toAdmin(1);
        break;
      case "ADMINISTRATIVE":
        admin = AdminTypeConvert.toAdmin(2);
        break;
      case "PSYCHOPEDAGOGUE":
        admin = AdminTypeConvert.toAdmin(3);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + adminType);
    }

    return managerDao.getManagersByType(admin);
  }

  public List<Manager> getManagers(int school_id) throws ApiException {
    return managerService.getManagers(school_id);
  }
}
