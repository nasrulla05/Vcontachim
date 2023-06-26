package com.akhbulatov.vcontachim.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.utility.SharedPreferencesManager

class AuthViewModel : ViewModel() {

    val authUrl = "https://oauth.vk.com/authorize?" +
            "client_id=51611155" +
            "&redirect_uri=https://oauth.vk.com/blank.html" +
            "&scope=offline,photos,video,wall,friends,groups" +
            "&display=mobile" +
            "&response_type=token"

    fun getAccessToken(url: Uri) {
        val urlString = url.toString()
        val urlDecoder = Uri.decode(urlString)
        val finalUrlDecoded = Uri.decode(urlDecoder)
        if (finalUrlDecoded.contains("access_token", ignoreCase = false)) {
            val afterAccessToken = finalUrlDecoded.substringAfter("access_token=")
            val beforeAccessToken = afterAccessToken.substringBefore("&")
            SharedPreferencesManager.saveToken(beforeAccessToken)

            if (beforeAccessToken.isNotEmpty()) {
                VcontachimApplication.router.navigateTo(Screens.mainAc())
            }
        }
    }
}