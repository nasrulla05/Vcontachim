package com.akhbulatov.vcontachim

import android.content.Intent
import com.akhbulatov.vcontachim.activity.LaunchActivity
import com.akhbulatov.vcontachim.activity.MainActivity
import com.akhbulatov.vcontachim.activity.PhotoActivity
import com.akhbulatov.vcontachim.activity.VideoCommActivity
import com.akhbulatov.vcontachim.fragments.*
import com.akhbulatov.vcontachim.model.Item
import com.akhbulatov.vcontachim.model.PhotosAlbums
import com.akhbulatov.vcontachim.model.Video
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

    fun videoPlayerFr(
        item: Video.Item
    ) = FragmentScreen { VideoPlayerFragment.createVideoPlayer(item) }

    fun commentsFr(item: Item) =
        FragmentScreen { PhotoCommentsFragment.createFragment(item) }

    fun videoComments(video: Video.Item) =
        FragmentScreen { VideoCommentsFragment.createFragment(video) }

    fun infoProfile(id: Long) = FragmentScreen { InfoProfileFragment.createFragment(id) }
    fun userId(id: Long) = FragmentScreen { InfoProfileFragment.uploadingYourId(id) }
    fun searchFf() = FragmentScreen { UserSearchFragment() }
    fun launchAc() = ActivityScreen { Intent(it, LaunchActivity::class.java) }
    fun mainAc() = ActivityScreen { Intent(it, MainActivity::class.java) }
    fun photoAc(itemPhoto: Item) =
        ActivityScreen { PhotoActivity.createActivity(context = it, itemPhoto) }

    fun videoCommAc(video: Video.Item) =
        ActivityScreen { VideoCommActivity.createFragment(context = it, video) }
}