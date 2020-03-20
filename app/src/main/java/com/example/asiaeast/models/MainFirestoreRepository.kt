package com.example.asiaeast.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async

class MainFirestoreRepository {

    private var firestoreDB = FirebaseFirestore.getInstance() //access to Firebase cloud DB
    var user = FirebaseAuth.getInstance().currentUser!!


    private val destinationRef = firestoreDB.collection("destinations")


    //suspend fun fetchDestinations(): QuerySnapshot? =
    //    firestoreDB.collection("destinations").get().await()

    fun getDestinations(): CollectionReference {
        var collectionReference = firestoreDB.collection("destinations")
        return collectionReference
    }
}