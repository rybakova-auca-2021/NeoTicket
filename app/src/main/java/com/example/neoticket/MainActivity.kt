package com.example.neoticket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.neoticket.Utils.NetworkUtils
import com.example.neoticket.Utils.Util
import com.example.neoticket.view.auth.RegisterFragment
import com.example.neoticket.view.main.InternetDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (NetworkUtils.isNetworkAvailable(this)) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
            val navController = navHostFragment.navController

            bottomNavigationView = findViewById(R.id.bottomNavigationView)
            bottomNavigationView.setupWithNavController(navController)

            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.btn_main -> {
                        navController.navigate(R.id.mainPageFragment)
                        true
                    }

                    R.id.btn_my_tickets -> {
                        if (Util.token != null) {
                            navController.navigate(R.id.myTicketsFragment)
                        } else {
                            val bottomSheetFragment = RegisterFragment()
                            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                        }
                        true
                    }

                    R.id.btn_info -> {
                        navController.navigate(R.id.infoPageFragment)
                        true
                    }

                    R.id.btn_profile -> {
                        if (Util.token != null) {
                            navController.navigate(R.id.profileFragment)
                        } else {
                            val bottomSheetFragment = RegisterFragment()
                            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                        }
                        true
                    }

                    else -> false
                }
            }
        } else {
            val bottomSheetFragment = InternetDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    fun hideBtmNav() {
        val navBar = findViewById<View>(R.id.bottomNavigationView)
        navBar.visibility = View.GONE
    }

    fun showBtmNav() {
        val navBar = findViewById<View>(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
    }
}
