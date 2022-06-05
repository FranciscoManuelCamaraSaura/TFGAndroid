/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 22/03/21 20:32 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: ApiService.java.
 * Last modified: 22/03/21 20:32.
 */

package com.tfgandroid.schoolmanager.data.access.api;

import com.tfgandroid.schoolmanager.data.access.api.entities.Login;
import com.tfgandroid.schoolmanager.data.access.database.entities.Alert;
import com.tfgandroid.schoolmanager.data.access.database.entities.Course;
import com.tfgandroid.schoolmanager.data.access.database.entities.Event;
import com.tfgandroid.schoolmanager.data.access.database.entities.Exam;
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
import com.tfgandroid.schoolmanager.data.enums.Degree;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
  @GET("school")
  Call<List<School>> getSchoolsCall();

  @GET("school/{id}")
  Call<School> getSchoolCall(@Path("id") Integer id);

  /*@GET("course/{school}")
  Call<List<Course>> getCoursesCall(@Field("school") Integer school);*/

  @GET("course/{id}")
  Call<Course> getCourseCall(@Path("id") Integer course_id);

  /*@GET("group/{id}")
  Call<Group> getBelongsGroupCall(@Field("id") Integer id);

  @GET("group")
  Call<List<Group>> getGroupCall(
      @Path("course_id") Integer course_id, @Path("group_words") Character group_words);*/

  @GET("person/{dni}")
  Call<Person> getPerson(@Path("dni") String dni);

  @GET("impart")
  Call<List<Impart>> getImpartCall(
      @Query("course_id") Integer course_id, @Query("group_words") String group_words);

  @GET("group_have_preceptor")
  Call<Teacher> getGroupHavePreceptorCall(
      @Path("group_words") Character group_words,
      @Path("course_number") Integer course_number,
      @Path("course_degree") Degree course_degree);

  @GET("student/{legal_guardian}")
  Call<List<Student>> getStudentCall(@Path("legal_guardian") String legal_guardian);

  @GET("managers/{school_id}")
  Call<List<Manager>> getManagerCall(@Path("school_id") Integer school_id);

  @GET("events")
  Call<List<Event>> getEventsCall(
      @Query("school_id") Integer school_id, @Query("student_id") Integer student_id);

  @GET("exams")
  Call<List<Exam>> getExamsCall(
      @Query("course_id") Integer course_id, @Query("group_words") String group_words);

  @GET("makes")
  Call<List<Makes>> getMakesCall(@Query("student") Integer student);

  @GET("messageSent/{sender}")
  Call<List<Message>> getSentMessagesCall(@Path("sender") String sender);

  @GET("messageReceived/{receiver}")
  Call<List<Message>> getReceivedMessagesCall(@Path("receiver") String receiver);

  @GET("alertReceived/{receiver}")
  Call<List<Alert>> getReceivedAlertsCall(@Path("receiver") String receiver);

  @FormUrlEncoded
  @POST("messageRead")
  Call<String> setReadMessage(@Field("id") Integer id);

  @FormUrlEncoded
  @POST("messageReply")
  Call<String> setReplyMessage(@Field("id") Integer id);

  @POST("messageUpdate")
  Call<Message> updateData(@Body Message message);

  @FormUrlEncoded
  @POST("alertRead")
  Call<String> setReadAlert(@Field("id") Integer id);

  @FormUrlEncoded
  @POST("subjects")
  Call<List<Subject>> getSubjectsCall(@Field("subjects_id") String subjects_id);

  @FormUrlEncoded
  @POST("teachers")
  Call<List<Person>> getTeachersCall(@Field("teachers_id") String teachers_id);

  @FormUrlEncoded
  @POST("managers")
  Call<List<Person>> getManagersCall(@Field("managers_id") String managers_id);

  @POST("legal_guardian")
  Call<LegalGuardian> loginCall(@Body Login login);

  @PUT("person")
  Call<Person> updatePersonCall(@Body Person person);

  @PUT("legal_guardian")
  Call<LegalGuardian> updateLegalGuardianCall(@Body LegalGuardian legal_guardian);

  @PUT("messageSender")
  Call<Message> sendMessageCall(@Body Message sent);
}
