package com.akhbulatov.vcontachim.utility

import android.content.Context
import android.content.SharedPreferences
import com.akhbulatov.vcontachim.VcontachimApplication

object SharedPreferencesManager {

    private val sharedPreferences: SharedPreferences =
        VcontachimApplication.context.getSharedPreferences(
            "vcontachim_preferences",
            Context.MODE_PRIVATE
        )
    private const val TOKEN = "access_token"
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    val accessToken: String? = sharedPreferences.getString(TOKEN, null)

    fun saveToken(beforeAccessToken: String) {
        editor.putString(TOKEN, beforeAccessToken)
        editor.apply()
    }
}