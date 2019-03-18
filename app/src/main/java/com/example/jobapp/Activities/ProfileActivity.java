package com.example.jobapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobapp.Adapters.FirestoreAdapter;
import com.example.jobapp.Models.Profile;
import com.example.jobapp.R;

import java.util.InputMismatchException;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    FirestoreAdapter firestoreAdapter = new FirestoreAdapter();
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
        Log.d(TAG, "onCreate:  ================= > " + profile.toString());

        employer_name.setText(profile.getUsername());
        employer_email.setText(profile.getContactEmail());
        employer_about.setText(profile.getAbout());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.profile_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Profile newProfile = new Profile(firestoreAdapter.userUID(), employer_name.getText().toString().trim()
                            , employer_email.getText().toString().trim()
                            , employer_about.getText().toString().trim());

                    firestoreAdapter.updateProfile(newProfile);
                    Intent intent = new Intent(ProfileActivity.this, DrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(ProfileActivity.this, "Your profile has been successfully updated", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
