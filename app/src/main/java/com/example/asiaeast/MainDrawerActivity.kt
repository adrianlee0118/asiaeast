package com.example.asiaeast

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.asiaeast.models.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    //Add a viewmodel class containing the variables above as livedata so that their state persists no matter what fragments are created

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.main_nav_host)

        val mainViewModel:MainViewModel by viewModels()       //Create viewmodel for the first time, persists through lifecycle of activity (through all fragments)

        appBarConfiguration = AppBarConfiguration.Builder(
                R.id.editInputsFragment,
                R.id.mapFragment,
                R.id.schedulePreviewFragment
            )
            .setDrawerLayout(main_drawer_layout)
            .build()

        setSupportActionBar(main_toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)

        visibilityNavElements(navController)
    }

    private fun visibilityNavElements(navController: NavController) {

        //makes the two menus appear or disappear depending on which fragment is showing
        //in this case, bottom navigation only shows when we are on the schedule preview fragment

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mapFragment -> hideBothNavigation()
                R.id.editInputsFragment -> hideBottomNavigation()
                else -> showBothNavigation()
            }
        }

    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        main_bottom_navigation_view?.visibility = View.GONE
        main_navigation_view?.visibility = View.GONE
        main_drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        main_bottom_navigation_view?.visibility = View.GONE
        main_navigation_view?.visibility = View.VISIBLE
        main_drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer

        main_navigation_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    private fun showBothNavigation() {
        main_bottom_navigation_view?.visibility = View.VISIBLE
        main_navigation_view?.visibility = View.VISIBLE
        main_drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl() //To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {        //attach the menu layouts to the drawer and the bottom navigation structures
        main_navigation_view?.setupWithNavController(navController)
        main_bottom_navigation_view?.setupWithNavController(navController)
    }

    fun exitApp() { //To exit the application call this function from fragment
        this.finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            main_drawer_layout.isDrawerOpen(GravityCompat.START) -> {
                main_drawer_layout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed() //If drawer is already in closed condition then go back
            }
        }
    }
}