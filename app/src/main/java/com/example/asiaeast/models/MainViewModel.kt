package com.example.asiaeast.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class MainViewModel : ViewModel() {

    val TAG = "MAIN_VIEW_MODEL"
    private var country = ""              //default values
    private var city = ""
    private var days = -1

    var firebaseRepository = MainFirestoreRepository()
    private val destinationList: MutableLiveData<List<Destination>> = MutableLiveData()

    // get realtime updates from firebase regarding saved destinations
    fun getDestinationList(): LiveData<List<Destination>>{
        firebaseRepository.getDestinations(country,city).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                destinationList.value = null
                return@EventListener
            }

            var savedDestinations : MutableList<Destination> = mutableListOf()
            for (doc in value!!) {
                var destinationItem = doc.toObject(Destination::class.java)
                savedDestinations.add(destinationItem)
            }
            destinationList.value = savedDestinations
        })

        return destinationList
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