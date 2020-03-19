package com.example.asiaeast.models

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

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
}