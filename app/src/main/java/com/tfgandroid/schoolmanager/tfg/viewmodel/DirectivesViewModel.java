/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 18/9/21 20:20 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: DirectivesViewModel.java.
 * Last modified: 18/9/21 20:20.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.School;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import com.tfgandroid.schoolmanager.data.repository.CourseRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import com.tfgandroid.schoolmanager.data.repository.SchoolRepository;
import com.tfgandroid.schoolmanager.data.repository.StudentRepository;
import java.util.List;

public class DirectivesViewModel extends AndroidViewModel {
  private final CourseRepository courseRepository;
  private final PersonRepository personRepository;
  private final SchoolRepository schoolRepository;
  private final StudentRepository studentRepository;
  private TypeError type;

  public DirectivesViewModel(Application application) {
    super(application);

    courseRepository = CourseRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
    schoolRepository = SchoolRepository.getInstance(application);
    studentRepository = StudentRepository.getInstance(application);
  }

  public MutableLiveData<School> getSchool(String legalGuardian) {
    MutableLiveData<School> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              Student student = studentRepository.getStudentSaved(legalGuardian);
              Course course = courseRepository.getCourseSaved(student.getCourse_id());
              School school = schoolRepository.getSchool(course.getSchool());

              mutableLiveData.postValue(school);
            })
        .start();

    return mutableLiveData;
  }

  public MutableLiveData<List<Person>> getManagers() {
    MutableLiveData<List<Person>> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              List<Person> managers = personRepository.getManagersSaved();

              mutableLiveData.postValue(managers);
            })
        .start();

    return mutableLiveData;
  }

  public TypeError getType() {
    return type;
  }

  public void setType(TypeError type) {
    this.type = type;
  }
}
