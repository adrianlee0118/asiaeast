package com.example.asiaeast

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

class InputFragmentDirections private constructor() {
    companion object {
        fun actionInputFragmentToMapFragment(): NavDirections =
            ActionOnlyNavDirections(R.id.action_editInputsFragment_to_mapFragment)
    }
}