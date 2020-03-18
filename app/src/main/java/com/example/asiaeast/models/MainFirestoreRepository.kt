package com.example.asiaeast.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async

class MainFirestoreRepository {

    private val firestoreDB = FirebaseFirestore.getInstance()       //access to Firebase cloud DB
    private val destinationRef = firestoreDB.collection("destinations")

    var user = FirebaseAuth.getInstance().currentUser!!

    suspend fun fetchDestinations(): QuerySnapshot? =
        firestoreDB.collection("destinations").get().await()

    companion object {
        @Volatile
        private var instance: MainFirestoreRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MainFirestoreRepository().also { instance = it }
            }
    }
}