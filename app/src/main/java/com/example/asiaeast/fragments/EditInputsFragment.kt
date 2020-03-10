package com.example.asiaeast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.asiaeast.InputFragmentDirections
import com.example.asiaeast.MainDrawerActivity
import com.example.asiaeast.R
import kotlinx.android.synthetic.main.fragment_edit_inputs.*

class EditInputsFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_inputs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Initialising navController

        //This variable enables us to control the content of the city spinner based on the selection in the country spinner
        var cities = R.array.citydefault

        //Set the country spinner
        val countryadapter = ArrayAdapter.createFromResource(
            getActivity()!!.getBaseContext(), R.array.countries,
            R.layout.spinner_item
        )
        countryadapter.setDropDownViewResource(R.layout.spinner_item)
        country_spinner.adapter = countryadapter
        country_spinner.setPrompt("Please Select")


        country_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                cities = when (country_spinner.selectedItem.toString()) {
                    "South Korea" -> R.array.korean_cities
                    "Japan" -> R.array.japanese_cities
                    "Taiwan" -> R.array.taiwanese_cities
                    "Thailand" -> R.array.thai_cities
                    "Vietnam" -> R.array.vietnamese_cities
                    "China" -> R.array.chinese_cities
                    else -> R.array.citydefault
                }
                Toast.makeText(
                    getActivity()!!.getBaseContext(),
                    getString(R.string.selected_item) + " " + "" + country_spinner.selectedItem.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    getActivity()!!.getBaseContext(),
                    "Please select a country.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //Set the city spinner
        val cityadapter = ArrayAdapter.createFromResource(
            getActivity()!!.getBaseContext(), cities,
            R.layout.spinner_item
        )
        cityadapter.setDropDownViewResource(R.layout.spinner_item)
        city_spinner.adapter = cityadapter
        city_spinner.setPrompt("Please Select")

        city_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    getActivity()!!.getBaseContext(),
                    getString(R.string.selected_item) + " " + "" + city_spinner.selectedItem.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                        getActivity()!!.getBaseContext(),
                        "Please select a city.",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }

        val dayadapter = ArrayAdapter.createFromResource(
            getActivity()!!.getBaseContext(), R.array.days,
            R.layout.spinner_item
        )
        dayadapter.setDropDownViewResource(R.layout.spinner_item)
        day_spinner.adapter = dayadapter
        day_spinner.setPrompt("Please Select")

        day_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    getActivity()!!.getBaseContext(),
                    getString(R.string.selected_item) + " " + "" + day_spinner.selectedItem.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    getActivity()!!.getBaseContext(),
                    "Please select a duration.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        GoButton.setOnClickListener(this)
        /*country_spinner.selectedItem.toString()
        city_spinner.selectedItem.toString()
        daySpinner.selectedItem.toString()          //to be sent up to main activity*/
    }

    override fun onClick(v: View?) { //When click occurs this function triggers a function based on id of clicked view
        when (v!!.id) {
            R.id.GoButton -> goToNextFragment()
        }
    }

    private fun goToNextFragment() {
        val action =
            InputFragmentDirections.actionInputFragmentToMapFragment() //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }
}