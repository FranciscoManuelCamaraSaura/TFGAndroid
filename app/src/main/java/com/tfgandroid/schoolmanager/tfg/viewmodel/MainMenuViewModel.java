/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 9/9/21 18:00 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MainMenuViewModel.java.
 * Last modified: 9/9/21 18:00.
 */

package com.tfgandroid.schoolmanager.tfg.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.tfgandroid.schoolmanager.data.access.api.error.TypeError;
import com.tfgandroid.schoolmanager.data.access.api.service.exceptions.ApiException;
import com.tfgandroid.schoolmanager.data.access.database.entities.Impart;
import com.tfgandroid.schoolmanager.data.access.database.entities.Manager;
import com.tfgandroid.schoolmanager.data.access.database.entities.Person;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import com.tfgandroid.schoolmanager.data.repository.GroupHavePreceptorRepository;
import com.tfgandroid.schoolmanager.data.repository.ImpartRepository;
import com.tfgandroid.schoolmanager.data.repository.ManagerRepository;
import com.tfgandroid.schoolmanager.data.repository.MessageRepository;
import com.tfgandroid.schoolmanager.data.repository.PersonRepository;
import com.tfgandroid.schoolmanager.data.repository.StudentRepository;
import com.tfgandroid.schoolmanager.data.repository.SubjectRepository;
import com.tfgandroid.schoolmanager.data.repository.TeacherRepository;
import java.util.List;

public class MainMenuViewModel extends AndroidViewModel {
  private final GroupHavePreceptorRepository groupHavePreceptorRepository;
  private final ImpartRepository impartRepository;
  private final ManagerRepository managerRepository;
  private final MessageRepository messageRepository;
  private final PersonRepository personRepository;
  private final StudentRepository studentRepository;
  private final SubjectRepository subjectRepository;
  private final TeacherRepository teacherRepository;
  private TypeError type;

  public MainMenuViewModel(Application application) {
    super(application);
    groupHavePreceptorRepository = GroupHavePreceptorRepository.getInstance(application);
    impartRepository = ImpartRepository.getInstance(application);
    managerRepository = ManagerRepository.getInstance(application);
    messageRepository = MessageRepository.getInstance(application);
    personRepository = PersonRepository.getInstance(application);
    studentRepository = StudentRepository.getInstance(application);
    subjectRepository = SubjectRepository.getInstance(application);
    teacherRepository = TeacherRepository.getInstance(application);
  }

  public void getData(String legalGuardian, int idSchool) {
    new Thread(
            () -> {
              try {
                Student student = studentRepository.getStudentSaved(legalGuardian);
                List<Impart> imparts =
                    impartRepository.getImpart(student.getCourse_id(), student.getGroup_words());

                // String[] subjectsId = getSubjectsIdVector(imparts);
                // String[] teachersId = geTeachersIdVector(imparts);

                List<Person> teachers = personRepository.getTeachers(getTeachersId(imparts));
                List<Manager> managers = managerRepository.getManagers(idSchool);

                subjectRepository.getSubjects(getSubjectsId(imparts));
                teacherRepository.setTeachers(teachers);
                personRepository.getManagers(getManagersId(managers));
                managerRepository.setManagers(managers);
                messageRepository.getMessagesSent(legalGuardian);
                messageRepository.getMessagesReceived(legalGuardian);

                for (Impart impart : imparts) {
                  impartRepository.insert(impart);
                }

                // falta recuperar makes y events
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

  public String[] getSubjectsIdVector(List<Impart> imparts) {
    String[] subjectsId = new String[imparts.size()];

    for (int i = 0; i < imparts.size(); i++) {
      subjectsId[i] = imparts.get(i).getSubject();
    }

    return subjectsId;
  }

  public String[] getTeachersIdVector(List<Impart> imparts) {
    String[] teachersId = new String[imparts.size()];

    for (int i = 0; i < imparts.size(); i++) {
      teachersId[i] = imparts.get(i).getTeacher();
    }

    return teachersId;
  }

  public String getSubjectsId(List<Impart> imparts) {
    StringBuilder subjectsId = new StringBuilder();

    for (int i = 0; i < imparts.size(); i++) {
      subjectsId.append(imparts.get(i).getSubject());

      if (i < imparts.size() - 1) {
        subjectsId.append(", ");
      }
    }

    return subjectsId.toString();
  }

  public String getTeachersId(List<Impart> imparts) {
    StringBuilder teachersId = new StringBuilder();

    for (int i = 0; i < imparts.size(); i++) {
      teachersId.append(imparts.get(i).getTeacher());

      if (i < imparts.size() - 1) {
        teachersId.append(", ");
      }
    }

    return teachersId.toString();
  }

  public String getManagersId(List<Manager> managers) {
    StringBuilder managersId = new StringBuilder();

    for (int i = 0; i < managers.size(); i++) {
      managersId.append(managers.get(i).getPerson());

      if (i < managers.size() - 1) {
        managersId.append(", ");
      }
    }

    return managersId.toString();
  }
}
