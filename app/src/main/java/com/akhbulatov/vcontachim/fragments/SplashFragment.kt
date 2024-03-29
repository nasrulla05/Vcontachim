package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.utility.SharedPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(3000)

            val authorizedUser = SharedPreferencesManager.accessToken
            if (authorizedUser == null) {
                VcontachimApplication.router.replaceScreen(Screens.loginFr())
            } else {
                VcontachimApplication.router.replaceScreen(Screens.mainAc())
            }
        }
    }
}
