package com.akhbulatov.vcontachim

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.akhbulatov.vcontachim.network.VcontachimService
import com.akhbulatov.vcontachim.utility.SharedPreferencesManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import okhttp3.*
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

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    val url: HttpUrl = request
                        .url
                        .newBuilder()
                        .addQueryParameter("v", "5.131")
                        .build()

                    request = request
                        .newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer ${SharedPreferencesManager.accessToken}"
                        )
                        .url(url)
                        .build()

                    return chain.proceed(request)
                }
            })

            .addInterceptor(ChuckerInterceptor(context))
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        vcontachimService = retrofit.create()
    }

    companion object {
        lateinit var vcontachimService: VcontachimService

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigateHolder: NavigatorHolder
    }
}