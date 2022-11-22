package com.example.cine.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ListsViewModel  extends ViewModel {


    private FirebaseFirestore db;
    FirebaseUser user;

    private MutableLiveData<Map<String, Object>> listsMovies;

    public MutableLiveData<Map<String, Object>> getListsMovies( ) {
        if (listsMovies == null) {
            listsMovies = new MutableLiveData<Map<String, Object>>();
            loadMovies(listsMovies);
        }
        return listsMovies;
    }

    private void loadMovies(MutableLiveData<Map<String, Object>> movies){
        user = FirebaseAuth.getInstance().getCurrentUser();
        db= FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document( user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        movies.setValue(document.getData());
                    } else {
                        Log.d("firebase", "No such document");
                    }
                } else {
                    Log.d("firebase", "get failed with ", task.getException());
                }
            }
        });
    }
}
