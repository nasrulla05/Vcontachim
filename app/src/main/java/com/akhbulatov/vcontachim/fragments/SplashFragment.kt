package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.SplashBinding
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.splash) {
    var binding: SplashBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SplashBinding.bind(view)

        lifecycleScope.launch {
            delay(300)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}