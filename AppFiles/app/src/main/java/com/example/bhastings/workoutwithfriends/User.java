package com.example.bhastings.workoutwithfriends;

/**
 * Created by bhastings on 11/3/2016.
 */

public class User {
    
    String username, firstName, lastName, password, photoLink;
    Integer weight, goalWeight, height, age;

    //class will send user data for the database for register
    public User (String username, String firstName, String lastName, String password, Integer weight, Integer goalWeight, Integer height, Integer age, String photoLink){

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.height = height;
        this.age = age;
        this.photoLink = photoLink;
    }

    //class will access data from the database for login
    public User (String username, String password){
        this.username = username;
        this.password = password;
        this.firstName = "";
        this.lastName = "";
        this.weight = null;
        this.goalWeight = null;
        this.height = null;
        this.age = null;
        this.photoLink = "";
    }


}
