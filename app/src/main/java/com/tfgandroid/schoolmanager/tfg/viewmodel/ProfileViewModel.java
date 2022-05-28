/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 28/04/21 18:42 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ProfileViewModel.java.
 * Last modified: 28/04/21 18:41.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import com.tfgandroid.schoolmanager.data.repository.CourseRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import com.tfgandroid.schoolmanager.data.repository.StudentRepository;

public class ProfileViewModel extends AndroidViewModel {
  private final CourseRepository courseRepository;
  private final StudentRepository studentRepository;
  private final PersonRepository personRepository;
  private TypeError type;

  public ProfileViewModel(Application application) {
    super(application);

    courseRepository = CourseRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
    studentRepository = StudentRepository.getInstance(application);
  }

  public MutableLiveData<Student> getStudent(LegalGuardian legalGuardian) {
    MutableLiveData<Student> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Student student = studentRepository.getStudentSaved(legalGuardian.getPerson());

              mutableLiveData.postValue(student);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Person> getPerson(LegalGuardian legalGuardian) {
    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Person person = personRepository.getPersonSaved(legalGuardian.getPerson());

              mutableLiveData.postValue(person);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<Course> getCourse(int course_id) {
    MutableLiveData<Course> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Course course = courseRepository.getCourseSaved(course_id);

              mutableLiveData.postValue(course);
            })
        .start();

    return mutableLiveData;
  }

  public void setPerson(Person person) {
    new Thread(
            () -> {
              try {
                personRepository.setPerson(person);
              } catch (ApiException e) {
                setType(e.getType());
              }
            })
        .start();
  }

  public TypeError getType() {
    return type;
  }

  public void setType(TypeError type) {
    this.type = type;
  }
}
