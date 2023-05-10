package com.akhbulatov.vcontachim

import android.content.Intent
import com.akhbulatov.vcontachim.activity.LaunchActivity
import com.akhbulatov.vcontachim.activity.MainActivity
import com.akhbulatov.vcontachim.activity.PhotoActivity
import com.akhbulatov.vcontachim.fragments.*
import com.akhbulatov.vcontachim.model.Item
import com.akhbulatov.vcontachim.model.PhotosAlbums
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun splashFr() = FragmentScreen { SplashFragment() }
    fun loginFr() = FragmentScreen { LoginFragment() }
    fun authFr() = FragmentScreen { AuthFragment() }
    fun homeFr() = FragmentScreen { HomeFragment() }
    fun profileFr() = FragmentScreen { ProfileFragment() }
    fun friendsFr() = FragmentScreen { FriendsFragment() }
    fun communitiesFr() = FragmentScreen { CommunitiesFragment() }
    fun photoAlbumsFr() = FragmentScreen { PhotosAlbumsFragment() }
    fun photosFr(itemAlbum: PhotosAlbums.Items) =
        FragmentScreen { PhotosFragment.createFragment(itemAlbum) }

    fun videoFr() = FragmentScreen { VideoFragment() }
    fun photoFr(
        itemPhoto: Item
    ) = FragmentScreen { PhotoFragment.createPhoto(itemPhoto) }

    fun launchAc() = ActivityScreen { Intent(it, LaunchActivity::class.java) }
    fun mainAc() = ActivityScreen { Intent(it, MainActivity::class.java) }
    fun photoAc(
        itemPhoto: Item
    ) = ActivityScreen {
        PhotoActivity.createFragment(
            context = it,
            itemPhoto
        )
    }
}