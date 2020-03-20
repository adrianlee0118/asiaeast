package com.example.asiaeast.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainFirestoreRepository {

    private var firestoreDB = FirebaseFirestore.getInstance() //access to Firebase cloud DB
    var user = FirebaseAuth.getInstance().currentUser!!

    fun getDestinations(): CollectionReference {
        var collectionReference = firestoreDB.collection("destinations")
        return collectionReference
    }

    //TODO: Add authentication
}