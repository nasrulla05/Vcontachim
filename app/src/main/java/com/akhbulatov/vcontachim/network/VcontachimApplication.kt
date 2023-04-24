package com.akhbulatov.vcontachim.network

import android.app.Application
import android.content.Context
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

        vcontachim = retrofit.create()
    }

    companion object {
        lateinit var vcontachim: Vcontachim

        lateinit var context: Context

        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigateHolder: NavigatorHolder
    }
}