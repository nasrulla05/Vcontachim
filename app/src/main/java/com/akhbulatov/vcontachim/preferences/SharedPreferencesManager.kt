package com.akhbulatov.vcontachim.preferences

import android.content.Context
import android.content.SharedPreferences
import com.akhbulatov.vcontachim.VcontachimApplication

class SharedPreferencesManager() {
    private val sharedPreferences: SharedPreferences =
        VcontachimApplication.context.getSharedPreferences(
            "vcontachim_preferences",
            Context.MODE_PRIVATE
        )
    val accessToken: String? = sharedPreferences.getString("access_token", null)

    fun saveToken(beforeAccessToken: String) {
        val editor: SharedPreferences.Editor =
            VcontachimApplication.sharedPr.sharedPreferences.edit()
        editor.putString("access_token", beforeAccessToken)
        editor.apply()
    }
}