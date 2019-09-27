package com.example.jobapp.model;


public class Contract {

    public static class Ads {
        public static final String COLLECTION_NAME = "ads";
        public static final String USERNAME = "username";
        public static final String CONTACT_EMAIL = "contactEmail";
        public static final String POSITION = "position";
        public static final String HIGHLIGHT = "highlightedText";
        public static final String QUALIFICATION = "qualification";
        public static final String DESCRIPTION = "description";
        public static final String ABOUT = "about";
        public static final String UID = "uid";

        private Ads(){
        }
    }

    public static class Profiles {
        public static final String COLLECTION_NAME = "profiles";
        public static final String UID = "uid";
        public static final String USERNAME = "username";
        public static final String CONTACT_EMAIL = "contactEmail";
        public static final String ABOUT = "about";


        private Profiles(){
        }
    }
}