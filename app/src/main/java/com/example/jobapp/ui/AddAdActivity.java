package com.example.jobapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobapp.R;
import com.example.jobapp.model.Ad;
import com.example.jobapp.repository.FirestoreAdapter;

public class AddAdActivity extends AppCompatActivity{
    final FirestoreAdapter firestoreAdapter = new FirestoreAdapter();
    private EditText editPosition;
    private EditText editHighlight;
    private EditText editQualification;
    private EditText editDescription;
    private Button editButton;
    private Button deleteButton;
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
        editButton = findViewById(R.id.new_ad_edit_button);
        deleteButton = findViewById(R.id.new_ad_delete_button);
        Intent intent = getIntent();
        ad = (Ad) intent.getSerializableExtra("Ad");
        id = intent.getStringExtra("Id");

        if(id != null) {
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setText(getString(R.string.edit_ad_button_text));
            editPosition.setText(ad.getPosition());
            editHighlight.setText(ad.getHighlightedText());
            editDescription.setText(ad.getDescription());
            editQualification.setText(ad.getQualification());

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firestoreAdapter.deleteAd(id);
                    Intent intent = new Intent(AddAdActivity.this, AdListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(AddAdActivity.this, "Ad has been successfully deleted", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firestoreAdapter.addToFirestore(editPosition.getText().toString()
                            , editHighlight.getText().toString()
                            , editQualification.getText().toString()
                            , editDescription.getText().toString()
                            , firestoreAdapter.userUID(), id);
                    Intent intent = new Intent(AddAdActivity.this, AdListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(AddAdActivity.this, "Ad has been successfully updated", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

        } else {
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firestoreAdapter.addToFirestore(editPosition.getText().toString()
                            , editHighlight.getText().toString()
                            , editQualification.getText().toString()
                            , editDescription.getText().toString()
                            , firestoreAdapter.userUID(), null);
                    Intent intent = new Intent(AddAdActivity.this, AdListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(AddAdActivity.this, "Ad has been successfully added", Toast.LENGTH_LONG).show();
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
