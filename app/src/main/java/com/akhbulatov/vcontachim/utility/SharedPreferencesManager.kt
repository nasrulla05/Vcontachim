package com.akhbulatov.vcontachim.utility

import android.content.Context
import android.content.SharedPreferences
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.HistoryUser
import com.google.gson.Gson

object SharedPreferencesManager {

    private val sharedPreferences: SharedPreferences =
        VcontachimApplication.context.getSharedPreferences(
            "vcontachim_preferences",
            Context.MODE_PRIVATE
        )
    private const val TOKEN = "access_token"
    private const val HISTORY = "HISTORY"
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    val accessToken: String? = sharedPreferences.getString(TOKEN, null)

    fun saveToken(beforeAccessToken: String) {
        editor.putString(TOKEN, beforeAccessToken)
        editor.apply()
    }

    fun saveHistory(list:List<HistoryUser>){
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(HISTORY,json)
        editor.apply()
    }

}