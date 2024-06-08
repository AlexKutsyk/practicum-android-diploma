package ru.practicum.android.diploma.util


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.practicum.android.diploma.App

fun isConnected(): Boolean {
    val connectivityManager = App.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
        }
    }
    return false
}
