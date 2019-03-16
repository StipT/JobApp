package com.example.jobapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.jobapp.Adapters.FirebaseAuthAdapter;
import com.example.jobapp.Adapters.FirestoreAdapter;
import com.example.jobapp.Adapters.RecyclerAdapter;
import com.example.jobapp.Models.Ad;
import com.example.jobapp.Models.Contract;
import com.example.jobapp.Models.Profile;
import com.example.jobapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditAdsActivity extends AppCompatActivity {

    private static final String TAG = "EditAdsActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerAdapter recyclerAdapter;
    private FirestoreAdapter firestoreAdapter = new FirestoreAdapter();
    private FirebaseAuthAdapter firebaseAuthAdapter = new FirebaseAuthAdapter();
    private Profile currentProfile;
    FirestoreRecyclerOptions<Ad> options;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ads);
        setUpRecyclerViewe();
        currentProfile = firestoreAdapter.getCurrentProfile();
    }

    private void setUpRecyclerViewe() {

        options = new FirestoreRecyclerOptions.Builder<Ad>()
                .setQuery(db.collection(Contract.Ads.COLLECTION_NAME)
                        .whereEqualTo(Contract.Ads.UID, firebaseAuthAdapter.userUID()), Ad.class)
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
        Intent intent = new Intent(EditAdsActivity.this, DrawerActivity.class);
        startActivity(intent);
    }
}