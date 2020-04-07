package com.example.asiaeast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.asiaeast.R
import com.example.asiaeast.models.Destination
import com.example.asiaeast.models.MainViewModel

class SchedulePreviewFragment : Fragment() {

    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()   //declaration allows access to root activity viewmodel
    private lateinit var destinations: LiveData<List<Destination>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =
            findNavController() //Initialising navController and linking UI to mainViewModel's data using observers

        destinations = mainViewModel.getDestinations()
        if (destinations == null) {
            Toast.makeText(
                getActivity()!!.getBaseContext(),
                "Please choose a city first in input activity.",
                Toast.LENGTH_SHORT
            ).show()
        }

        makeSched()
    }

    fun makeSched() {

    }
}