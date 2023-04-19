package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.network.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.FragmentLoginBinding

class LoginFragment:Fragment(R.layout.fragment_login) {
   private var binding:FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding!!.enter.setOnClickListener {
            VcontachimApplication.router.navigateTo(Screens.authFr())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}