package com.akhbulatov.vcontachim.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var binding: ActivityMainBinding? = null
    private var navigator = AppNavigator(activity = this, R.id.action_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout: View = findViewById(R.id.activity_main)
        binding = ActivityMainBinding.bind(rootLayout)

        if (savedInstanceState == null) {
            VcontachimApplication.router.replaceScreen(Screens.homeFr())
        }

        binding!!.bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.home) VcontachimApplication.router.replaceScreen(Screens.homeFr())
            if (item.itemId == R.id.profile) VcontachimApplication.router.replaceScreen(Screens.profileFr())
            if (item.itemId == R.id.searchMenu) VcontachimApplication.router.replaceScreen(Screens.searchFf())
            true
        }
    }

    override fun onResume() {
        super.onResume()
        VcontachimApplication.navigateHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        VcontachimApplication.navigateHolder.removeNavigator()
    }

}