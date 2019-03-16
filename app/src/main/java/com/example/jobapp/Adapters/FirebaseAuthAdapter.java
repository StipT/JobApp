package com.example.jobapp.Adapters;


import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthAdapter {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public String userUID(){
        return firebaseAuth.getUid();
    }


}
