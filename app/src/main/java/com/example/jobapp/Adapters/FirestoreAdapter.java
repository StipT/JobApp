package com.example.jobapp.Adapters;


import android.support.annotation.NonNull;
import android.util.Log;
import com.example.jobapp.Models.Contract;
import com.example.jobapp.Models.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FirestoreAdapter {
    private static final String TAG = "FirestoreAdapter";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuthAdapter firebaseAuthAdapter = new FirebaseAuthAdapter();
    private Profile currentProfile;



    public interface FirestoreListener {
        void onFoundProfile(Profile profile);
    }


    public Profile getCurrentProfile() {
        firestoreProfile(new FirestoreListener() {

            @Override
            public void onFoundProfile(Profile profile) {
                currentProfile = profile;
            }
        });
        return currentProfile;
    }

    public void firestoreProfile(final FirestoreListener listener) {
        db.collection(Contract.Profiles.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Profile matchedProfile = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Profile profile = document.toObject(Profile.class);

                                if (profile.getUid().equals(firebaseAuthAdapter.userUID())) {
                                    matchedProfile = profile;
                                }
                            }
                            listener.onFoundProfile(matchedProfile);
                        }
                    }
                });
    }

    public void addToFirestore(String position, String highlight, String qualification, String description, String uid, final String id) {
        final Map<String, String> mappedAd = new HashMap<>();
        mappedAd.put(Contract.Ads.USERNAME, getCurrentProfile().getUsername());
        mappedAd.put(Contract.Ads.CONTACT_EMAIL, getCurrentProfile().getContactEmail());
        mappedAd.put(Contract.Ads.ABOUT, getCurrentProfile().getAbout());
        mappedAd.put(Contract.Ads.POSITION, position);
        mappedAd.put(Contract.Ads.HIGHLIGHT, highlight);
        mappedAd.put(Contract.Ads.QUALIFICATION, qualification);
        mappedAd.put(Contract.Ads.DESCRIPTION, description);
        mappedAd.put(Contract.Ads.UID, uid);

        if (id != null) {
            db.collection(Contract.Ads.COLLECTION_NAME).document(id)
                    .set(mappedAd);
        } else {
            db.collection(Contract.Ads.COLLECTION_NAME)
                    .add(mappedAd)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Document added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Error adding document ", e);
                        }
                    });
        }
    }
    public void createProfile() {
        final Map<String, String> mappedEmployer = new HashMap<>();
        mappedEmployer.put(Contract.Profiles.UID, firebaseAuthAdapter.userUID());
        mappedEmployer.put(Contract.Profiles.USERNAME, "Guest");
        mappedEmployer.put(Contract.Profiles.CONTACT_EMAIL, "");
        mappedEmployer.put(Contract.Profiles.ABOUT, "I am just a guest until i personalise this profile");
        db.collection(Contract.Profiles.COLLECTION_NAME).add(mappedEmployer);
    }

    public void updateProfile(Profile profile) {
        final Map<String, String> mappedEmployer = new HashMap<>();
        mappedEmployer.put(Contract.Profiles.UID, profile.getUid());
        mappedEmployer.put(Contract.Profiles.USERNAME, profile.getUsername());
        mappedEmployer.put(Contract.Profiles.CONTACT_EMAIL, profile.getContactEmail());
        mappedEmployer.put(Contract.Profiles.ABOUT, profile.getAbout());

            db.collection(Contract.Ads.COLLECTION_NAME)
                    .whereEqualTo(Contract.Ads.UID, firebaseAuthAdapter.userUID())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : list) {
                                    Log.d(TAG, "onSuccess: d.getID  " + d.getId());
                                    db.collection(Contract.Ads.COLLECTION_NAME)
                                            .document(d.getId())
                                            .set(mappedEmployer, SetOptions.merge());
                                    Log.d(TAG, "onSuccess: ==============> " + d.toString());
                                }
                            }
                        }
                    });
            db.collection(Contract.Profiles.COLLECTION_NAME)
                    .whereEqualTo(Contract.Profiles.UID, firebaseAuthAdapter.userUID())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : list) {
                                    Log.d(TAG, "onSuccess: d.getID  " + d.getId());
                                    db.collection(Contract.Profiles.COLLECTION_NAME)
                                            .document(d.getId())
                                            .set(mappedEmployer, SetOptions.merge());

                                    Log.d(TAG, "onSuccess: ==============> " + d.toString());
                                }
                            }
                        }
                    });
        }
    }

