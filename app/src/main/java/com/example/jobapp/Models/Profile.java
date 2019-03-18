package com.example.jobapp.Models;

import java.io.Serializable;

public class Profile implements Serializable {
    private String uid;
    private String username;
    private String contactEmail;
    private String about;

    public Profile(){
    }

    public Profile(String uid,String username, String email, String about){
        this.uid = uid;
        this.username = username;
        this.contactEmail = email;
        this.about = about;

    }

    public String getUsername() {
        return username;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getAbout() {
        return about;
    }

    public String getUid(){
        return uid;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "uid='" + uid + '\'' +
                ", name='" + username + '\'' +
                ", email='" + contactEmail + '\'' +
                '}';
    }
}
