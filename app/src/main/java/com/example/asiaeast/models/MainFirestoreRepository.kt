package com.example.asiaeast.models

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainFirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    private var firestoreDB = FirebaseFirestore.getInstance() //access to Firebase cloud DB
    var user = FirebaseAuth.getInstance().currentUser!!

    suspend fun getDestinations(country : String, city: String): List<Destination> {
        var data = mutableListOf<Destination>()
        return try{
            firestoreDB.collection("destinations")
                .whereEqualTo("country",country)
                .whereEqualTo("city",city)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        data.add(Destination( document["Location"], document["country"], document["city"], document["name"], document["type"]))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
            return data
        } catch (e : Exception){
            return data
        }
    }
}