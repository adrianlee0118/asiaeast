package com.example.asiaeast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.asiaeast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val countries = resources.getStringArray(R.array.countries)

        // access the spinner
        if (binding.countrySpinner != null) {
            val adapter = ArrayAdapter(this, R.layout.spinner_item, countries)
            adapter.setDropDownViewResource(R.layout.spinner_item)
            binding.countrySpinner.adapter = adapter
            binding.countrySpinner.setPrompt("Please Select")


            binding.countrySpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@MainActivity, getString(R.string.selected_item) + " " + "" + countries[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(this@MainActivity, "Please select a country", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
