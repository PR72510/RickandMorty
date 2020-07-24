package com.example.rickandmorty.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by PR72510 on 24/7/20.
 */
object AppUtils {

     fun isInternetAvailable(context: Context): Boolean {
         val result: Boolean
         val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val networkCapabilities = connectivityManager.activeNetwork ?: return false
         val actNw =
             connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
         result = when {
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
             else -> false
         }

         return result
    }
}