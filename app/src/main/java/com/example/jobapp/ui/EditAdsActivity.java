package com.example.jobapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jobapp.R;
import com.example.jobapp.model.Ad;
import com.example.jobapp.model.Contract;
import com.example.jobapp.model.Profile;
import com.example.jobapp.repository.FirestoreAdapter;
import com.example.jobapp.ui.recycler_adapter.RecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditAdsActivity extends AppCompatActivity {

    private static final String TAG = "EditAdsActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerAdapter recyclerAdapter;
    private FirestoreAdapter firestoreAdapter = new FirestoreAdapter();
    private Profile currentProfile;
    FirestoreRecyclerOptions<Ad> options;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ads);
        setUpRecyclerView();
        currentProfile = firestoreAdapter.getCurrentProfile();
    }

    private void setUpRecyclerView() {

        options = new FirestoreRecyclerOptions.Builder<Ad>()
                .setQuery(db.collection(Contract.Ads.COLLECTION_NAME)
                        .whereEqualTo(Contract.Ads.UID, firestoreAdapter.userUID()), Ad.class)
                .build();

        recyclerAdapter = new RecyclerAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_edit_list);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerAdapter);


        recyclerAdapter.setOnClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Ad ad = documentSnapshot.toObject(Ad.class);
                String id = documentSnapshot.getId();

                Intent intent = new Intent(EditAdsActivity.this, AddAdActivity.class);
                intent.putExtra("Ad", ad);
                intent.putExtra("Id",id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(recyclerAdapter == null){
            Log.d(TAG, "onStart: null");
        } else {
            recyclerAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditAdsActivity.this, AdListActivity.class);
        startActivity(intent);
    }
}