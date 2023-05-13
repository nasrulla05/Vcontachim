package com.akhbulatov.vcontachim

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.akhbulatov.vcontachim.model.SharedPreferences
import com.akhbulatov.vcontachim.network.VcontachimService
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class VcontachimApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        cicerone = Cicerone.create()
        router = cicerone.router
        navigateHolder = cicerone.getNavigatorHolder()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        vcontachimService = retrofit.create()
        sharedPr = SharedPreferences()
    }

    companion object {
        lateinit var sharedPr:SharedPreferences
        lateinit var vcontachimService: VcontachimService

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigateHolder: NavigatorHolder
    }
}