package com.example.asiaeast.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class MainFirestoreRepository {

    private var firestoreDb = Firebase.firestore
    var user = FirebaseAuth.getInstance().currentUser!!

    suspend fun fetchDestinations(): QuerySnapshot? =
        firestoreDb.collection("destinations").get().await()

    companion object {
        @Volatile
        private var instance: MainFirestoreRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MainFirestoreRepository().also { instance = it }
            }
    }
}