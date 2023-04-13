package com.akhbulatov.vcontachim

import com.akhbulatov.vcontachim.fragments.LoginFragment
import com.akhbulatov.vcontachim.fragments.SplashFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun splashFr() = FragmentScreen { SplashFragment() }
    fun loginFr() = FragmentScreen { LoginFragment() }
}