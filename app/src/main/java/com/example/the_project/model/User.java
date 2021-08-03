package com.example.the_project.model;

public class User {
    private String name,email,password,profile_pic,uid;

    public User(String name, String email, String password, String profile_pic) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile_pic = profile_pic;
    }
    public User(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String name, String email, String password, String profile_pic, String uid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile_pic = profile_pic;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
