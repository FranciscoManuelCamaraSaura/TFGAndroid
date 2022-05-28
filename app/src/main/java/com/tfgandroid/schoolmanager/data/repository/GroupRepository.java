/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 18:32 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: GroupRepository.java.
 * Last modified: 3/03/21 18:32.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.GroupService;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;

public class GroupRepository {
  private static GroupRepository instance;
  private static GroupService groupService;
  private final GroupDAO groupDao;

  private GroupRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    groupDao = appDatabase.groupDAO();
    groupService = GroupService.getInstanceService(application);
  }

  public static GroupRepository getInstance(Application application) {
    if (instance == null) {
      instance = new GroupRepository(application);
    }
    return instance;
  }

  public void insert(Group newGroup) {
    if (newGroup != null) {
      Group group = groupDao.getGroup(newGroup.getCourse_id(), newGroup.getGroup_words());

      if (group == null) {
        groupDao.insert(newGroup);
      } else {
        groupDao.update(newGroup);
      }
    }
  }

  /*public Group getGroup(int course_id, char group_words) throws ApiException {
    return groupService.getGroup(course_id, group_words).get(0);
  }*/
}
