package com.example.asiaeast.models

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MainViewModel : ViewModel() {

    private val firestoreDB = FirebaseFirestore.getInstance()       //access to Firebase cloud DB
    private val destinationRef = firestoreDB.collection("destinations")

    private var country = ""                                                        //default values
    private var city = ""
    private var days = -1
    private lateinit var destinations: MutableLiveData<List<Destination>>           //to be provided by database

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