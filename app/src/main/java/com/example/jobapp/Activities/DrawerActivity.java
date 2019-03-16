package com.example.jobapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import com.example.jobapp.Adapters.FirestoreAdapter;
import com.example.jobapp.Adapters.RecyclerAdapter;
import com.example.jobapp.Models.Ad;
import com.example.jobapp.Models.Contract;
import com.example.jobapp.Models.Profile;
import com.example.jobapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "DrawerActivity";
    private RecyclerAdapter recyclerAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreAdapter firestoreAdapter = new FirestoreAdapter();
    private Profile currentUserProfile;
    private NavigationView navigationView;
    private View headerView;
    private TextView drawerUsername;
    private TextView drawerEmail;
    private SearchView searchView;

//TODO ScreenSize & landscape optimization


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        drawerUsername = headerView.findViewById(R.id.drawer_header_name);
        drawerEmail = headerView.findViewById(R.id.drawer_header_email);

        setUpRecyclerView("");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }

    private void setUpRecyclerView(String search) {
        FirestoreRecyclerOptions<Ad> options;

        if (search.isEmpty()) {
            options = new FirestoreRecyclerOptions.Builder<Ad>()
                    .setQuery(db.collection(Contract.Ads.COLLECTION_NAME), Ad.class)
                    .build();
        } else {
            options = new FirestoreRecyclerOptions.Builder<Ad>()
                    .setQuery(db.collection(Contract.Ads.COLLECTION_NAME).whereEqualTo(Contract.Ads.POSITION, search), Ad.class)
                    .build();
        }
        recyclerAdapter = new RecyclerAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerAdapter);


        recyclerAdapter.setOnClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Ad ad = documentSnapshot.toObject(Ad.class);
                Intent intent = new Intent(DrawerActivity.this, AdDetailsActivity.class);
                intent.putExtra("Ad", ad);

                startActivity(intent);
            }
        });
            }






    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firestoreAdapter.firestoreProfile(new FirestoreAdapter.FirestoreListener() {
            @Override
            public void onFoundProfile(Profile profile) {
                if (profile != null) {
                    currentUserProfile = profile;
                    drawerUsername.setText(profile.getUsername());
                    drawerEmail.setText(profile.getContactEmail());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);

    return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            setUpRecyclerView(searchView.getQuery().toString());

            //TODO SearchActivity filter
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_Profile) {
            Intent intent = new Intent(DrawerActivity.this, ProfileActivity.class);
            intent.putExtra("Profile", currentUserProfile);
            startActivity(intent);

        } else if (id == R.id.nav_edit_ads) {
            Intent intent = new Intent(DrawerActivity.this, EditAdsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_add_ad) {
            Intent intent = new Intent(DrawerActivity.this, AddAdActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
