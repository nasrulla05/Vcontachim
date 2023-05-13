package com.akhbulatov.vcontachim.model

import android.content.Context
import android.content.SharedPreferences
import com.akhbulatov.vcontachim.VcontachimApplication

class SharedPreferences() {
     val sharedPreferences: SharedPreferences =
        VcontachimApplication.context.getSharedPreferences(
            "vcontachim_preferences",
            Context.MODE_PRIVATE
        )
    val accessToken:String? = sharedPreferences.getString("access_token", null)
}