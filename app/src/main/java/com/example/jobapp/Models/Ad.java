package com.example.jobapp.Models;

import java.io.Serializable;


public class Ad implements Serializable {
    public static final long serialVesrionUID = 1L;
    private String username;
    private String contactEmail;
    private String position;
    private String highlightedText;
    private String description;
    private String qualification;
    private String about;

    //TODO  Timestamp/Date implementation

    public Ad(){
    }

    public Ad(String username, String contactEmail, String position, String highlightedText, String description, String qualification,String about, String id) {
        this.username = username;
        this.contactEmail = contactEmail;
        this.position = position;
        this.highlightedText = highlightedText;
        this.description = description;
        this.qualification = qualification;
        this.about = about;
    }


    public String getUsername() {
        return username;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getPosition() {
        return position;
    }

    public String getHighlightedText() {
        return highlightedText;
    }


    public String getDescription() {
        return description;
    }

    public String getQualification() {
        return qualification;
    }


    public String getAbout() {
        return about;
    }


    @Override
    public String toString() {
        return "Ad{" +
                "username='" + username + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", position='" + position + '\'' +
                ", highlightedText='" + highlightedText + '\'' +
                ", description='" + description + '\'' +
                ", qualification='" + qualification + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}




