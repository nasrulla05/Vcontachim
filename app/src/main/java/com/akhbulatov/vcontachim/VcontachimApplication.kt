package com.akhbulatov.vcontachim

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.akhbulatov.vcontachim.network.VcontachimService
import com.akhbulatov.vcontachim.preferences.SharedPreferencesManager
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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


        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${sharedPr.accessToken}")
                    .build()
                return chain.proceed(request)
            }
        })
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        vcontachimService = retrofit.create()
        sharedPr = SharedPreferencesManager()
    }

    companion object {
        lateinit var sharedPr: SharedPreferencesManager
        lateinit var vcontachimService: VcontachimService

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigateHolder: NavigatorHolder
    }
}