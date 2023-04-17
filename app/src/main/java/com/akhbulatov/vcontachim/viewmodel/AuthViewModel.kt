package com.akhbulatov.vcontachim.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.akhbulatov.vcontachim.VcontachimApplication

class AuthViewModel : ViewModel() {

    val authUrl = "https://oauth.vk.com/authorize?" +
            "client_id=51611155&" +
            "redirect_uri=https://oauth.vk.com/blank.html&" +
            "scope=12&" +
            "display=mobile&" +
            "response_type=token"

    fun getAccessToken(url: Uri) {
        val urlString = url.toString()
        val urlDecoder = Uri.decode(urlString)
        val finalUrlDecoded = Uri.decode(urlDecoder)
        if (finalUrlDecoded.contains("access_token", ignoreCase = false)) {
            val afterAccessToken = finalUrlDecoded.substringAfter("access_token=")
            val beforeAccessToken = afterAccessToken.substringBefore("&")

            val sharedPreferences: SharedPreferences =
                VcontachimApplication.context.getSharedPreferences(
                    "vcontachim_preferences",
                    Context.MODE_PRIVATE
                )
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("access_token", beforeAccessToken)
            editor.apply()
        }
    }

}