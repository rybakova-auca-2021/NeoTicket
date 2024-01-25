package com.example.neoticket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.neoticket.Utils.NetworkUtils
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
        } else {
            val bottomSheetFragment = InternetDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}
