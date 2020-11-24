package com.reazon.foodrunner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.fragment.*
import com.reazon.foodrunner.model.User

class MainActivity : AppCompatActivity(){//,Communicator{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem:MenuItem? = null

    var user:User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)

        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)



        setUpToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openHome()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.home ->{
                    openHome()
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment()).commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.favourite_restaurants ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,FavouriteFragment()).commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.orderHistory ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,HomeFragment()).commit()
                    supportActionBar?.title = "Order"
                    drawerLayout.closeDrawers()                }
                R.id.faq ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,FAQFragment()).commit()
                    supportActionBar?.title = "FAQ"
                    drawerLayout.closeDrawers()
                }
                R.id.logout ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,LogOutFragment()).commit()
                    supportActionBar?.title = "Log Out"
                    drawerLayout.closeDrawers()
                }

            }
            return@setNavigationItemSelectedListener true
        }

    }

    private fun setUpToolbar() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == item.itemId){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openHome(){
        val transaction =  supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,HomeFragment())
        transaction.commit()
//        val titleName = intent.getStringExtra("username").toString()
        supportActionBar?.title = "All Restaurants"
        drawerLayout.closeDrawers()
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {

        when(supportFragmentManager.findFragmentById(R.id.frame)){
            !is HomeFragment -> openHome()
            else -> super.onBackPressed()
        }

    }

    /*override fun passData(time: String) {
        val bundle = Bundle()
        bundle.putString("time_order",time)
        val fragNext = CartFragment()
        fragNext.arguments = bundle
    }*/

}


