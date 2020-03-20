package com.example.asiaeast.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class MainViewModel : ViewModel() {

    private val TAG = "MAIN_VIEW_MODEL"
    private var country = ""              //default values
    private var city = ""
    private var days = -1

    private val firebaseRepository = MainFirestoreRepository()
    private val destinations: MutableLiveData<List<Destination>> = MutableLiveData()

    // get realtime updates from Firestore DB regarding saved destinations, filter by city
    fun getDestinations(): LiveData<List<Destination>> {
        if (city == "") {
            destinations.value = null
            return destinations
        }

        firebaseRepository
            .getDestinations()
            .whereEqualTo("city", city)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    destinations.value = null
                    return@EventListener
                }

                var destlist: MutableList<Destination> = mutableListOf()
                for (doc in value!!) {
                    var destinationItem = doc.toObject(Destination::class.java)
                    destlist.add(destinationItem)
                }
                destinations.value = destlist
            })

        return destinations
    }

    fun getCountry(): String {
        return country
    }

    fun setCountry(ncountry: String) {
        country = ncountry
        //change filter on dataset
    }

    fun getCity(): String {
        return city
    }

    fun setCity(ncity: String) {
        city = ncity
        //change filter on city
    }

    fun getDays(): Int {
        return days
    }

    fun setDays(ndays: Int) {
        days = ndays
    }
}