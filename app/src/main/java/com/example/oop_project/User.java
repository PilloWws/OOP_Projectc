package com.example.oop_project;

public class User extends Person{
    public User(){
        super("user");
    }
    public User(String email, String password, long timestamp, String profileImage, String uid){
        super(email, password, timestamp, profileImage, uid);
    }
}
