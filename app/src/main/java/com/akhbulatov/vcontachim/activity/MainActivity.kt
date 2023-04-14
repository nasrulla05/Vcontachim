package com.akhbulatov.vcontachim.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Если параметр savedInstanceState равен null , то это обычный запуск приложения
        // Если параметр savedInstanceState равен НЕ null , то это уже поворот экрана

        if (savedInstanceState == null) {
            VcontachimApplication.router.navigateTo(Screens.splashFr())
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