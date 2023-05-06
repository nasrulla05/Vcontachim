package com.akhbulatov.vcontachim.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ContainerBinding
import com.akhbulatov.vcontachim.model.Photos
import com.github.terrakok.cicerone.androidx.AppNavigator

class PhotoActivity:AppCompatActivity(R.layout.container) {
    private var navigator = AppNavigator(this, R.id.photo_container)
    private var binding:ContainerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout: View = findViewById(R.id.container)
        binding = ContainerBinding.bind(rootLayout)

        if (savedInstanceState == null) {
            VcontachimApplication.router.navigateTo(Screens.photoFr())
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