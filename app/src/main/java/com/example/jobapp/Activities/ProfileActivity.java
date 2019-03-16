package com.example.jobapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.jobapp.Adapters.FirebaseAuthAdapter;
import com.example.jobapp.Adapters.FirestoreAdapter;
import com.example.jobapp.Models.Profile;
import com.example.jobapp.R;

import java.util.InputMismatchException;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    FirestoreAdapter firestoreAdapter = new FirestoreAdapter();
    FirebaseAuthAdapter firebaseAuthAdapter = new FirebaseAuthAdapter();
    boolean editExisting;

    EditText employer_name;
    EditText employer_email;
    EditText employer_about;

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        employer_name = findViewById(R.id.profile_employer);
        employer_email = findViewById(R.id.profile_email);
        employer_about = findViewById(R.id.profile_about);

        Intent intent = getIntent();
        profile = (Profile) intent.getSerializableExtra("Profile");

        if (profile.getUid().equals(firebaseAuthAdapter.userUID())) {
            editExisting = true;
        } else {
            editExisting = false;
        }




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.profile_button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    firestoreAdapter.updateProfile(new Profile(firebaseAuthAdapter.userUID(), employer_name.getText().toString().trim()
                            , employer_email.getText().toString().trim()
                            , employer_about.getText().toString().trim()));
                    Intent intent = new Intent(ProfileActivity.this, DrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        profile = firestoreAdapter.getCurrentProfile();

        if (profile != null) {
            if (profile.getUsername() != null) {
                employer_name.setText(profile.getUsername());
            }
            if (profile.getContactEmail() != null) {
                employer_email.setText(profile.getContactEmail());
            }
            if (profile.getAbout() != null) {
                employer_about.setText(profile.getAbout());
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
