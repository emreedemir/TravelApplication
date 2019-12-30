package com.emreedemir.travelapplication.Model;

import androidx.annotation.NonNull;

import com.emreedemir.travelapplication.Model.Enums.Gender;

import java.io.Serializable;

public class Customer implements Serializable
{
    String name;
    String age;
    String phoneNumber;
    String gender;

    public Customer(String name, String age, String phoneNumber, String gender) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NonNull
    @Override
    public String toString() {
        return name +" " +age +" "+ " "+gender +" " +phoneNumber;
    }
}
