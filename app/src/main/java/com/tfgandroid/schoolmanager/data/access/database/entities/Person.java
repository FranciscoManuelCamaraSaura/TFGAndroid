/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: Person.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Objects;

@Entity(
    tableName = "person",
    indices = @Index(value = "dni", unique = true))
public class Person {
  @PrimaryKey private final int id;
  @NonNull private String dni;
  private String name;
  private String surnames;
  private String address;
  private String location;
  private String province;
  private String postal_code;
  private String phone;
  private String email;

  public Person(
      int id,
      @NonNull String dni,
      String name,
      String surnames,
      String address,
      String location,
      String province,
      String postal_code,
      String phone,
      String email) {
    this.id = id;
    this.dni = dni;
    this.name = name;
    this.surnames = surnames;
    this.address = address;
    this.location = location;
    this.province = province;
    this.postal_code = postal_code;
    this.phone = phone;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  @NonNull
  public String getDni() {
    return dni;
  }

  public void setDni(@NonNull String dni) {
    this.dni = dni;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurnames() {
    return surnames;
  }

  public void setSurnames(String surnames) {
    this.surnames = surnames;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getPostal_code() {
    return postal_code;
  }

  public void setPostal_code(String postalCode) {
    this.postal_code = postalCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Person)) {
      return false;
    }

    Person person = (Person) o;

    return getId() == person.getId()
        && getDni().equals(person.getDni())
        && getName().equals(person.getName())
        && getSurnames().equals(person.getSurnames())
        && getAddress().equals(person.getAddress())
        && getLocation().equals(person.getLocation())
        && getProvince().equals(person.getProvince())
        && getPostal_code().equals(person.getPostal_code())
        && getPhone().equals(person.getPhone())
        && getEmail().equals(person.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getDni(),
        getName(),
        getSurnames(),
        getAddress(),
        getLocation(),
        getProvince(),
        getPostal_code(),
        getPhone(),
        getEmail());
  }

  @NonNull
  @Override
  public String toString() {
    return "Person{"
        + "id="
        + id
        + ", dni='"
        + dni
        + '\''
        + ", name='"
        + name
        + '\''
        + ", surnames='"
        + surnames
        + '\''
        + ", address='"
        + address
        + '\''
        + ", location='"
        + location
        + '\''
        + ", province='"
        + province
        + '\''
        + ", postal_code='"
        + postal_code
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }
}
