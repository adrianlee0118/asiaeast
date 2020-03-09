package com.example.asiaeast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.asiaeast.databinding.MapConfigBinding


class MapConfigActivity : AppCompatActivity() {

    private lateinit var binding: MapConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.map_config)
        setSupportActionBar(binding.toolbar)
        
    }

}