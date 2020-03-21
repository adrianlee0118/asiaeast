package com.example.asiaeast.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainFirestoreRepository {

    private var firestoreDB = FirebaseFirestore.getInstance() //access to Firebase cloud DB
    var user = FirebaseAuth.getInstance().currentUser!!

    //suspend this function since it's a database operation, takes a long time
    //Get collection for mainViewModel to access Firestore DB, mainViewModel does the querying based on its variables
    fun getDestinations(): CollectionReference {
        var collectionReference = firestoreDB.collection("destinations")
        return collectionReference
    }

    //TODO: Add authentication
}