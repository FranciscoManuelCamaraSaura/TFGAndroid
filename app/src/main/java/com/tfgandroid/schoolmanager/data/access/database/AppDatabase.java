/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 16:58 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: AppDatabase.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database;

import android.content.Context;
import android.database.Cursor;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.tfgandroid.schoolmanager.data.access.database.convert.AdminTypeConvert;
import com.tfgandroid.schoolmanager.data.access.database.convert.DataTypeConvert;
import com.tfgandroid.schoolmanager.data.access.database.convert.DegreeTypeConverter;
import com.tfgandroid.schoolmanager.data.access.database.convert.EvaluationTypeConvert;
import com.tfgandroid.schoolmanager.data.access.database.convert.TypeExamTypeConvert;
import com.tfgandroid.schoolmanager.data.access.database.dao.AlertDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.CourseDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.EventDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.ExamDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.GroupHavePreceptorDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.ImpartDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.LegalGuardianDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.MakesDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.ManagerDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.MessageDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.PersonDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SchoolDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.StudentDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SubjectDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.TeacherDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;
import com.tfgandroid.schoolmanager.data.access.database.entities.GroupHavePreceptor;
import com.tfgandroid.schoolmanager.data.access.database.entities.Impart;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Makes;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Message;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.access.database.entities.Teacher;

@Database(
    entities = {
      School.class,
      Course.class,
      Group.class,
      Person.class,
      Message.class,
      Manager.class,
      Teacher.class,
      LegalGuardian.class,
      GroupHavePreceptor.class,
      Student.class,
      Subject.class,
      Impart.class,
      Event.class,
      Exam.class,
      Makes.class,
      Alert.class
    },
    version = 2,
    exportSchema = false)
@TypeConverters({
  AdminTypeConvert.class,
  DataTypeConvert.class,
  DegreeTypeConverter.class,
  EvaluationTypeConvert.class,
  TypeExamTypeConvert.class
})
public abstract class AppDatabase extends RoomDatabase {
  private static final int COLUMN_INDEX = 0;
  private static final String DATABASE_NAME = "school_database";
  private static final String QUERY = "SELECT name FROM sqlite_master WHERE type = 'table'";
  private static final String SCHOOL = "school";
  private static AppDatabase instance;

  public static synchronized AppDatabase getInstance(Context context) {
    if (instance == null) {
      instance =
          Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
              .build();
    }

    return instance;
  }

  public static void deleteDataBase() {
    if (instance != null) {
      Cursor tablas = instance.getOpenHelper().getReadableDatabase().query(QUERY);
      SupportSQLiteDatabase sqlDatabase = instance.getOpenHelper().getWritableDatabase();

      if (tablas.moveToFirst()) {
        while (!tablas.isAfterLast()) {
          sqlDatabase.delete(tablas.getString(COLUMN_INDEX), null, null);
          tablas.moveToNext();
        }
      }

      instance = null;
    }
  }

  public abstract SchoolDAO schoolDAO();

  public abstract CourseDAO courseDAO();

  public abstract GroupDAO groupDAO();

  public abstract PersonDAO personDAO();

  public abstract MessageDAO messageDAO();

  public abstract ManagerDAO managerDAO();

  public abstract LegalGuardianDAO legalGuardianDAO();

  public abstract StudentDAO studentDAO();

  public abstract TeacherDAO teacherDAO();

  public abstract GroupHavePreceptorDAO groupHavePreceptorDAO();

  public abstract SubjectDAO subjectDAO();

  public abstract ImpartDAO impartDAO();

  public abstract EventDAO eventDAO();

  public abstract ExamDAO examDAO();

  public abstract MakesDAO makesDAO();

  public abstract AlertDAO alertDAO();
}
