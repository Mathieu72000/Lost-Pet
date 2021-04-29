package com.example.lostpet.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ConnexionTest {

    companion object {

        fun checkIfInternetIsAvailable(context: Context?): Boolean? {
            if (context == null) return false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val network = connectivityManager.activeNetwork
                    if (network != null) {
                        val nc: NetworkCapabilities =
                            connectivityManager.getNetworkCapabilities(network)
                        return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    }
                } else {
                    val networkInfos = connectivityManager.allNetworkInfo
                    for (tempNetworkInfo in networkInfos) {
                        if (tempNetworkInfo.isConnected) {
                            return true
                        }
                    }
                }
            }
            return false
        }
    }
}