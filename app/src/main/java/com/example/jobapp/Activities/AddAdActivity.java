package com.example.jobapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.jobapp.Adapters.FirebaseAuthAdapter;
import com.example.jobapp.Models.Ad;
import com.example.jobapp.Adapters.FirestoreAdapter;

import com.example.jobapp.R;

public class AddAdActivity extends AppCompatActivity{
    FirebaseAuthAdapter firebaseAuthAdapter = new FirebaseAuthAdapter();
    final FirestoreAdapter firestoreAdapter = new FirestoreAdapter();
    private static final String TAG = "AddAdActivity";
    private EditText editPosition;
    private EditText editHighlight;
    private EditText editQualification;
    private EditText editDescription;
    private Button button;
    private Ad ad;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        editPosition = findViewById(R.id.new_ad_edit_position);
        editHighlight = findViewById(R.id.new_ad_edit_highlight);
        editQualification = findViewById(R.id.new_ad_edit_qualification);
        editDescription = findViewById(R.id.new_ad_edit_description);
        button = findViewById(R.id.new_ad_edit_button);
        Intent intent = getIntent();
        ad = (Ad) intent.getSerializableExtra("Ad");
        id = intent.getStringExtra("Id");

        if(id != null) {
            editPosition.setText(ad.getPosition());
            editHighlight.setText(ad.getHighlightedText());
            editDescription.setText(ad.getDescription());
            editQualification.setText(ad.getQualification());


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firestoreAdapter.addToFirestore(editPosition.getText().toString()
                            , editHighlight.getText().toString()
                            , editDescription.getText().toString()
                            , editQualification.getText().toString()
                            , firebaseAuthAdapter.userUID(), id);
                    Intent intent = new Intent(AddAdActivity.this, EditAdsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Snackbar.make(v,"Ad has been successfully edited", Snackbar.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

        } else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firestoreAdapter.addToFirestore(editPosition.getText().toString()
                            , editHighlight.getText().toString()
                            , editDescription.getText().toString()
                            , editQualification.getText().toString()
                            , firebaseAuthAdapter.userUID(), null);
                    Intent intent = new Intent(AddAdActivity.this, DrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Snackbar.make(v, "jobAd has been successfully added", Snackbar.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firestoreAdapter.getCurrentProfile();
    }
}