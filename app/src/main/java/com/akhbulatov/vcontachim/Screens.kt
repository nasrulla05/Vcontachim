package com.akhbulatov.vcontachim

import android.content.Intent
import com.akhbulatov.vcontachim.activity.MainActivity
import com.akhbulatov.vcontachim.fragments.*
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun splashFr() = FragmentScreen { SplashFragment() }
    fun loginFr() = FragmentScreen { LoginFragment() }
    fun authFr() = FragmentScreen { AuthFragment() }
    fun homeFr() = FragmentScreen { HomeFragment() }
    fun profileFr() = FragmentScreen { ProfileFragment() }
    fun mainAc() = ActivityScreen { Intent(it, MainActivity::class.java) }
    fun friendsFr() = FragmentScreen{FriendsFragment()}
    fun communitiesFr() = FragmentScreen{CommunitiesFragment()}
    fun photoAlbumsFr() = FragmentScreen{PhotosAlbumsFragment()}
}