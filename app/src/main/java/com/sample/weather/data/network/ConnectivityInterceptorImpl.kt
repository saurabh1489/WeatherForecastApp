package com.sample.weather.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.sample.weather.di.AppContext
import com.sample.weather.internal.ConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectivityInterceptorImpl @Inject constructor(@AppContext context: Context) : ConnectivityInterceptor {

    private val isOnline by lazy {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return@lazy connectionCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || connectionCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline) {
            throw ConnectivityException()
        }
        return chain.proceed(chain.request())
    }
}