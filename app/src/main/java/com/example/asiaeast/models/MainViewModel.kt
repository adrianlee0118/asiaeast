package com.example.asiaeast.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val country = MutableLiveData<String>("")
    private val city = MutableLiveData<String>("")
    private val days = MutableLiveData<Int>(-1)
    //private val locations = MutableLiveData<List<Location_Records>>()           to be provided by database



    fun getCountry(): LiveData<String> {
        return country
    }

    fun setCountry(ncountry: String) {
        country.value = ncountry
    }

    fun getCity(): LiveData<String> {
        return city
    }

    fun setCity(ncity: String) {
        city.value = ncity
    }

    fun getDays(): LiveData<Int> {
        return days
    }

    fun setDays(ndays: Int) {
        days.value = ndays
    }
}