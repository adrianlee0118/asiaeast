package com.example.asiaeast.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val TAG = "MAIN_VIEW_MODEL"
    private var country = ""              //default values
    private var city = ""
    private var days = -1

    private val firebaseRepository = MainFirestoreRepository()
    private val destinations: MutableLiveData<List<Destination>> = MutableLiveData()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getDestinationsData()
    }

    fun getDestinationsData(){               //Coroutine scope wrapper for the heavy suspend function that does the DB loading asynchronously
        uiScope.launch {
            getDestinationsFromFirebase()
            //Modify fragment UI from here if using a real-time snapshot to monitor the database
        }
    }

    // get updates from Firestore DB for destinations asynchronously via coroutine, filter by city
    // can change to realtime updates if we use the Snapshot listener for when we migrate to TripAdvisor database or when we anticipate more live updates to the current
    suspend fun getDestinationsFromFirebase(){
        return withContext(Dispatchers.IO) {
            if (city == "") {
                destinations.value = null
            }

            firebaseRepository
                .getDestinations()
                .whereEqualTo("city", city)
                .get()
                .addOnSuccessListener { documents ->
                    var destlist: MutableList<Destination> = mutableListOf()
                    for (doc in documents!!) {
                        var destinationItem = doc.toObject(Destination::class.java)
                        destlist.add(destinationItem)
                    }
                    destinations.value = destlist
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
                /*.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
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
                })*/
        }
    }

    fun getDestinations(): LiveData<List<Destination>>{
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}