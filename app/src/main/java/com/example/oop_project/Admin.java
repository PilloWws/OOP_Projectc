package com.example.oop_project;

public class Admin extends Person{
    private String accountType = "admin";
    public Admin(){
        super("admin");
    }
    public Admin(String email, String password, long timestamp, String profileImage, String uid){
        super(email, password, timestamp, profileImage, uid);
    }
}
