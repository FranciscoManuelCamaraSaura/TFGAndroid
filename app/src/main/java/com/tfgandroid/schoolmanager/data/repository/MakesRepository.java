/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/03/21 19:55 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: MakesRepository.java.
 * Last modified: 22/03/21 19:55.
 */

package com.tfgandroid.schoolmanager.data.repository;

import android.app.Application;
import com.tfgandroid.schoolmanager.data.access.database.AppDatabase;
import com.tfgandroid.schoolmanager.data.access.database.dao.ExamDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.MakesDAO;
import com.tfgandroid.schoolmanager.data.access.database.dao.StudentDAO;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
import com.tfgandroid.schoolmanager.data.access.database.entities.Makes;
import com.tfgandroid.schoolmanager.data.access.database.entities.Student;
import java.util.List;

public class MakesRepository {
    private static MakesRepository instance;
    private final StudentDAO studentDao;
    private final ExamDAO examDao;
    private final MakesDAO makesDAO;

    private MakesRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);

        studentDao = appDatabase.studentDAO();
        examDao = appDatabase.examDAO();
        makesDAO = appDatabase.makesDAO();
    }

    public static MakesRepository getInstance(Application application) {
        if (instance == null) {
            instance = new MakesRepository(application);
        }

        return instance;
    }

    public void insert(Makes newMakes) {
        if(newMakes != null) {
            Student student = studentDao.getStudentById(newMakes.getStudent());
            Exam exam = examDao.getExam(newMakes.getExam());

            if (student != null && exam != null) {
                List<Integer> exams = makesDAO.getExam(student.getId());

                if(!exams.contains(exam.getId())) {
                    makesDAO.insert(newMakes);
                } else {
                    makesDAO.update(newMakes);
                }
            }
        }
    }
}
