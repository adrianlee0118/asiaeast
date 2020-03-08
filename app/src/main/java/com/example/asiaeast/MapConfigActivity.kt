package com.example.asiaeast

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment

class MapConfigActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fragmentBonjour: Fragment? = null;
    private var fragmentHello: Fragment? = null;
    private var fragmentOla: Fragment? = null;

    private val FRAGMENT_BONJOUR_ID = 0
    private val FRAGMENT_HELLO_ID = 1
    private val FRAGMENT_OLA_ID = 2

    /**
     * Display a fragment
     */
    private fun afficherFragment(identifiantDuFragment: Int)
    {
        when (identifiantDuFragment)
        {
            FRAGMENT_BONJOUR_ID -> this.creationEtAffichageDeBonjourFragment()
            FRAGMENT_HELLO_ID -> this.creationEtAffichageDeHelloFragment()
            FRAGMENT_OLA_ID -> this.creationEtAffichageDeOlaFragment()
        }
    }

    /**
     * Create the bonjour fragment
     */
    private fun creationEtAffichageDeBonjourFragment()
    {
        if (this.fragmentBonjour == null)
        {
            this.fragmentBonjour = BonjourFragment.newInstance()
        }
        this.chargementDuFragment(this.fragmentBonjour)
    }

    /**
     * Create the hello fragment
     */
    private fun creationEtAffichageDeHelloFragment() {
        if (this.fragmentHello == null) this.fragmentHello = HelloFragment.newInstance()
        this.chargementDuFragment(this.fragmentHello)
    }

    /**
     * Create the ola fragment
     */
    private fun creationEtAffichageDeOlaFragment() {
        if (this.fragmentOla == null) this.fragmentOla = OlaFragment.newInstance()
        this.chargementDuFragment(this.fragmentOla)
    }

    /**
     * Load a fragment into the activity
     */
    private fun chargementDuFragment(fragment: Fragment?) {
        if (fragment != null) {
            if (!fragment.isVisible) {
                supportFragmentManager.beginTransaction()
                    .replace(fragment_a_afficher.id, fragment).commit()
            }
        }
    }

    /**
     * OnCreate main function
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * Overriden so that we return to the drawer
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
        {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else
        {
            super.onBackPressed()
        }
    }

    /**
     * Inflate the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * Fonction onOptionsItemSelected de base auto-généré par l'assistant.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * Attach different fragments depending on the menu item
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.nav_camera -> this.afficherFragment(FRAGMENT_BONJOUR_ID)
            R.id.nav_gallery -> this.afficherFragment(FRAGMENT_HELLO_ID)
            R.id.nav_slideshow -> this.afficherFragment(FRAGMENT_OLA_ID)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}