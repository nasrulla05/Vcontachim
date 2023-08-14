package com.akhbulatov.vcontachim.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private var navigator = AppNavigator(activity = this, R.id.action_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            VcontachimApplication.router.replaceScreen(Screens.homeFr())
        }

        binding.bottomNavigation.apply {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFr -> VcontachimApplication.router.replaceScreen(Screens.homeFr())
                    R.id.searchMenu -> VcontachimApplication.router.replaceScreen(Screens.searchFf())
                    R.id.profile -> VcontachimApplication.router.replaceScreen(Screens.profileFr())
                }
                true
            }
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