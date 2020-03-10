package com.example.asiaeast

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.asiaeast.databinding.MapConfigBinding
import com.example.asiaeast.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.map_config.*
import kotlinx.android.synthetic.main.map_config.view.*


class MapConfigActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: MapConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.map_config)
        setSupportActionBar(binding.root.toolbar)

        binding.root.nav_view.setNavigationItemSelectedListener { this }

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.root.drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager().begin().replace(R.id.fragment_container, MapFragment())
                .commit()
            binding.root.nav_view.setCheckedItem(R.id.navItemMap)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.navItemEditPreferences -> supportFragmentManager().begin().replace(
                R.id.fragment_container,
                EditInputsFragment()
            ).commit()
            R.id.navItemMap -> supportFragmentManager().begin().replace(
                R.id.fragment_container,
                MapFragment()
            ).commit()
            R.id.navItemSchedPreview -> supportFragmentManager().begin().replace(
                R.id.fragment_container,
                SchedulePreviewFragment()
            ).commit()
            R.id.mapItemSendToEmail ->
        }

        binding.root.drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}