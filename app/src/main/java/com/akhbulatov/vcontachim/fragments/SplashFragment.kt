package com.akhbulatov.vcontachim.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.network.VcontachimApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(3000)

            val sharedPreferences = requireContext().getSharedPreferences(
                "vcontachim_preferences",
                Context.MODE_PRIVATE
            )
            val authorizedUser = sharedPreferences.getString("access_token", null)
            if (authorizedUser == null) {
                VcontachimApplication.router.navigateTo(Screens.loginFr())
            } else {
                VcontachimApplication.router.navigateTo(Screens.mainAc())
            }
        }
    }
}
