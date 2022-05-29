/*
 * Copyright (c) 2021. All rights reserved.
 * Designed and developed by Francisco Manuel Cámara Saura on 3/03/21 17:17 for the Final Project of the Degree in Computer Engineering of the University of Alicante.
 *
 * File name: School.java.
 * Last modified: 3/03/21 16:58.
 */

package com.tfgandroid.schoolmanager.data.access.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Objects;

@Entity(tableName = "school", indices = @Index("id"))
public class School {
  @PrimaryKey private final int id;
  private String name;
  private String address;
  private String location;
  private String province;
  private String phone;
  private String postal_code;
  private String web_site;

  public School(
      int id,
      String name,
      String address,
      String location,
      String province,
      String postal_code,
      String phone,
      String web_site) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.location = location;
    this.province = province;
    this.postal_code = postal_code;
    this.phone = phone;
    this.web_site = web_site;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public void setPostal_code(String postal_code) {
    this.postal_code = postal_code;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getWeb_site() {
    return web_site;
  }

  public void setWeb_site(String web_site) {
    this.web_site = web_site;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof School)) {
      return false;
    }

    School school = (School) o;

    return getId() == school.getId()
        && getName().equals(school.getName())
        && getAddress().equals(school.getAddress())
        && getLocation().equals(school.getLocation())
        && getProvince().equals(school.getProvince())
        && getPostal_code().equals(school.getPostal_code())
        && getPhone().equals(school.getPhone())
        && getWeb_site().equals(school.getWeb_site());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getName(),
        getAddress(),
        getLocation(),
        getProvince(),
        getPhone(),
        getPostal_code(),
        getWeb_site());
  }

  @NonNull
  @Override
  public String toString() {
    return "School{"
        + "id="
        + id
        + ", name='"
        + name
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
        + ", phone="
        + phone
        + ", postal_code='"
        + postal_code
        + '\''
        + ", web_site='"
        + web_site
        + '\''
        + '}';
  }
}
