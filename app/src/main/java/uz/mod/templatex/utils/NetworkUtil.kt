package uz.mod.templatex.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

object NetworkUtil {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) isConnectedUpToAndroidMarshmallow(cm)
        else isConnectedAndroidMarshmallowAndAbove(cm)
    }

    @Suppress("DEPRECATION")
    private fun isConnectedUpToAndroidMarshmallow(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true &&
                (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE ||
                        activeNetwork.type == ConnectivityManager.TYPE_VPN)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedAndroidMarshmallowAndAbove(connectivityManager: ConnectivityManager): Boolean {
        val networkInfo = connectivityManager.activeNetwork ?: return false
        val ncm = connectivityManager.getNetworkCapabilities(networkInfo) ?: return false
        return ncm.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                ncm.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                ncm.hasTransport(NetworkCapabilities.TRANSPORT_VPN)

    }
}