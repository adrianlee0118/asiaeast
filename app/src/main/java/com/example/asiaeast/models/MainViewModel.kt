package com.example.asiaeast.models

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class MainViewModel : ViewModel {

    private var country = ""                                                        //default values
    private var city = ""
    private var days = -1

    private val mutableSearchedDestinations: MutableLiveData<List<Destination>> = MutableLiveData()
    val searchedDestination: LiveData<List<Destination>> = mutableSearchedDestinations
    

    init {
        viewModelScope.launch {
            runCatching {
                MainFirestoreRepository.findAll()
            }.onSuccess { repos -> mutableSearchedDestinations.value = repos }
        }
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

    fun getDestinations(): LiveData<List<Destination>>{                                 //listener to update destination variable when db is changed
        destinationRef.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                destinations.value = null
                return@EventListener
            }

            var savedDestinations : MutableList<Destination> = mutableListOf()
            for (doc in value!!) {
                var DestinationItem = doc.toObject(Destination::class.java)
                savedDestinations.add(DestinationItem)
            }
            destinations.value = savedDestinations
        })

        return destinations
    }
}