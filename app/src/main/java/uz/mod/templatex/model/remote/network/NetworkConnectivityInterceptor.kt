package uz.mod.templatex.model.remote.network

import android.app.Application
import okhttp3.Interceptor
import okhttp3.Response
import uz.mod.templatex.R
import uz.mod.templatex.utils.NetworkUtil
import uz.mod.templatex.utils.NoConnectionException


class NetworkConnectivityInterceptor constructor(val application: Application) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (NetworkUtil.isConnected(application)) chain.proceed(chain.request())
        else throw NoConnectionException(application.getString(R.string.error_no_connection))
    }
}