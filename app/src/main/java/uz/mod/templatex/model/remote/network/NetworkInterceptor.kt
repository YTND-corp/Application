package uz.mod.templatex.model.remote.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Response
import uz.mod.templatex.R
import java.io.IOException



class NetworkInterceptor constructor(val application: Application): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw IOException(application.getString(R.string.error_no_connection))
        }

    }

    fun isConnected(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}