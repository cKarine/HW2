package com.example.hw2.usersDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.hw2.user.UserData;

@Entity(tableName = "users",
        indices = {@Index(value = {"first_name", "last_name"}, unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    @ColumnInfo(name = "age")
    public int age;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "image")
    public String image;

    public User(String firstName, String lastName, int age, String email, String city, String country, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.city = city;
        this.country = country;
        this.image = image;
    }

    public User(UserData userData) {
        this.firstName = userData.getFirstName();
        this.lastName = userData.getLastName();
        this.age = userData.getAge();
        this.email = userData.getEmail();
        this.city = userData.getCity();
        this.country = userData.getCountry();
        this.image = userData.getImage();
    }
}
