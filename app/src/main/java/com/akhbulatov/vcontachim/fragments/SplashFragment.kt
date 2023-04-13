package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.SplashFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.splash_fragment) {
    var binding: SplashFragmentBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SplashFragmentBinding.bind(view)

        lifecycleScope.launch {
            delay(3000)
            VcontachimApplication.router.navigateTo(Screens.homeFr())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}