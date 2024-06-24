package com.example.hw2.user;

public class UserData {
    private Name name;
    private String email;
    private Dob dob;
    private Location location;
    private Picture picture;
    private Id id;

    public String getFirstName() {
        return name.first;
    }

    public String getLastName() {
        return name.last;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return dob.age;
    }

    public String getCity() {
        return location.city;
    }

    public String getCountry() {
        return location.country;
    }

    public String getImage() {
        return picture.large;
    }

    public String getId() {
        return id.value;
    }
}

class Name {
    public String first;
    public String last;
}

class Dob {
    public int age;
}

class Location {
    public String city;
    public String country;
}

class Picture {
    public String large;
}

class Id {
    public String value;
}
