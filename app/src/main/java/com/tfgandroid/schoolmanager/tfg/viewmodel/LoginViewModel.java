/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 15/04/21 18:07 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: LoginViewModel.java.
 * Last modified: 15/04/21 18:00.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import com.tfgandroid.schoolmanager.data.access.database.entities.Group;
import com.tfgandroid.schoolmanager.data.access.database.entities.LegalGuardian;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import com.tfgandroid.schoolmanager.data.repository.CourseRepository;
import com.tfgandroid.schoolmanager.data.repository.GroupRepository;
import com.tfgandroid.schoolmanager.data.repository.LegalGuardianRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import com.tfgandroid.schoolmanager.data.repository.StudentRepository;

public class LoginViewModel extends AndroidViewModel {
  private final CourseRepository courseRepository;
  private final GroupRepository groupRepository;
  private final LegalGuardianRepository legalGuardianRepository;
  private final PersonRepository personRepository;
  private final StudentRepository studentRepository;
  private TypeError type;

  public LoginViewModel(Application application) {
    super(application);

    courseRepository = CourseRepository.getInstance(application);
    groupRepository = GroupRepository.getInstance(application);
    legalGuardianRepository = LegalGuardianRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
    studentRepository = StudentRepository.getInstance(application);
  }

  public MutableLiveData<LegalGuardian> legalGuardianLogin(Login login) {
    MutableLiveData<LegalGuardian> mutableLiveData = new MutableLiveData<>();

    new Thread(
            () -> {
              try {
                LegalGuardian legalGuardian = legalGuardianRepository.loginLegalGuardian(login);
                Person person = personRepository.getPerson(legalGuardian.getPerson());
                Student student = studentRepository.getStudent(legalGuardian.getPerson());
                Group group = new Group(0, student.getCourse_id(), student.getGroup_words());
                Course course = courseRepository.getCourse(student.getCourse_id());

                legalGuardian.setStudent_name(student.getName());

                personRepository.insert(person);
                legalGuardianRepository.insert(legalGuardian);
                courseRepository.insert(course);
                groupRepository.insert(group);
                studentRepository.insert(student);

                mutableLiveData.postValue(legalGuardian);
              } catch (ApiException e) {
                setType(e.getType());
                mutableLiveData.postValue(null);
              }
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
