package com.example.jobapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jobapp.R;
import com.example.jobapp.model.Ad;

public class AdDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        Intent intent = getIntent();
        Ad ad = (Ad) intent.getSerializableExtra("Ad");


        TextView name = findViewById(R.id.detail_name);
        TextView email = findViewById(R.id.detail_email);
        TextView position = findViewById(R.id.detail_position);
        TextView higlight = findViewById(R.id.detail_highlight);
        TextView description = findViewById(R.id.detail_description);
        TextView qualifications = findViewById(R.id.detail_qualification);
        TextView aboutHeader = findViewById(R.id.detail_about_header);
        TextView about = findViewById(R.id.detail_about);


        name.setText(ad.getUsername());
        email.setText(ad.getContactEmail());
        position.setText(ad.getPosition());
        higlight.setText(ad.getHighlightedText());
        description.setText(ad.getDescription());
        String aboutHeaderString = "About " + ad.getUsername() + ": ";
        aboutHeader.setText(aboutHeaderString);
        about.setText(ad.getAbout());
        qualifications.setText(ad.getQualification());
    }
}
