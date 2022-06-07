/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 20:04 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: PersonRepository.java.
 * Last modified: 3/03/21 20:04.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.api.service.PersonService;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.ImpartDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.ManagerDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.PersonDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.StudentDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.SubjectDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.TeacherDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Subject;
import com.tfgandroid.schoolmanager.data.access.database.entities.Teacher;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
  private static PersonRepository instance;
  private static PersonService personService;
  private final ImpartDAO impartDAO;
  private final ManagerDAO managerDAO;
  private final PersonDAO personDao;
  private final StudentDAO studentDao;
  private final SubjectDAO subjectDao;
  private final TeacherDAO teacherDao;

  private PersonRepository(Application application) {
    AppDatabase appDatabase = AppDatabase.getInstance(application);

    impartDAO = appDatabase.impartDAO();
    managerDAO = appDatabase.managerDAO();
    personDao = appDatabase.personDAO();
    studentDao = appDatabase.studentDAO();
    subjectDao = appDatabase.subjectDAO();
    teacherDao = appDatabase.teacherDAO();
    personService = PersonService.getInstanceService(application);
  }

  public static PersonRepository getInstance(Application application) {
    if (instance == null) {
      instance = new PersonRepository(application);
    }

    return instance;
  }

  public void insert(Person newPerson) {
    Person person = personDao.getPerson(newPerson.getDni());

    if (person == null) {
      personDao.insert(newPerson);
    } else {
      personDao.update(newPerson);
    }
  }

  public Person getPerson(String dni) throws ApiException {
    return personService.getPerson(dni);
  }

  public Person getPersonSaved(String dni) {
    return personDao.getPerson(dni);
  }

  public void setPerson(Person person) throws ApiException {
    Person updatePerson = personService.setPerson(person);
    insert(updatePerson);
  }

  public List<Person> getTeachers(String persons_dni) throws ApiException {
    List<Person> teachers = personService.getTeachers(persons_dni);

    for (Person teacher : teachers) {
      insert(teacher);
    }

    return teachers;
  }

  /*public List<Person> getTeachers(LegalGuardian legalGuardian) throws ApiException {
    Student student = studentDao.getStudent(legalGuardian.getPerson());
    List<String> teachersDni =
        impartDAO.getTeacher(student.getCourse_id(), student.getGroup_words());
    List<Person> teachers = personService.getTeachers(getTeachersId(teachersDni));

    for (Person teacher : teachers) {
      insert(teacher);
    }

    return teachers;
  }*/

  public List<Person> getTeachers(List<String> personsDni) {
    List<Person> teachers = new ArrayList<>();

    for (String personDni : personsDni) {
      teachers.add(getPersonSaved(personDni));
    }

    return teachers;
  }

  public List<Person> getTeachersSaved() {
    List<Person> person = new ArrayList<>();
    List<Teacher> teachers = teacherDao.getTeachers();

    for (Teacher teacher : teachers) {
      person.add(personDao.getPerson(teacher.getPerson()));
    }

    return person;
  }

  public List<Person> getManagers(String persons_dni) throws ApiException {
    List<Person> managers = personService.getManagers(persons_dni);

    for (Person manager : managers) {
      insert(manager);
    }

    return managers;
  }

  public List<Person> getManagersSaved() {
    List<Person> person = new ArrayList<>();
    List<Manager> managers = managerDAO.getManagers();

    for (Manager manager : managers) {
      person.add(personDao.getPerson(manager.getPerson()));
    }

    return person;
  }

  /*public String getTeachersId(List<String> teachers) {
    StringBuilder teachersId = new StringBuilder();

    for (int i = 0; i < teachers.size(); i++) {
      teachersId.append(teachers.get(i));

      if (i < teachers.size() - 1) {
        teachersId.append(", ");
      }
    }

    return teachersId.toString();
  }*/

  public List<Subject> getSubject(String person) {
    List<String> subjectsCode = impartDAO.getSubjects(person);
    List<Subject> subjects = new ArrayList<>();

    for (String subjectCode : subjectsCode) {
      subjects.add(subjectDao.getSubject(subjectCode));
    }

    return subjects;
  }

  public String getPersonName(String person) {
    return personDao.getPerson(person).getName();
  }
}
