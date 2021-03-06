package uz.mod.templatex.model.remote.network

import android.content.Context
import android.provider.Settings
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor constructor(val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        var url = req.url()
        val mobuid = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.e("Mobile-UID: ${mobuid}")
        var reqBuilder = req.newBuilder()
            .addHeader("Content-Type", "application/json")
//            .addHeader("language",prefs.selectedLanguage?.name?:"ru")
            .addHeader("Mobile-UID", mobuid)
//           .url(url)
//        prefs.token?.let {
//            reqBuilder.addHeader("Authorization", "Bearer " + it)
//        }
//        val versionName = BuildConfig.VERSION_NAME
//        reqBuilder.addHeader("version", "a${versionName}")

        req = reqBuilder.build()
        val responce: Response = chain.proceed(req)
        return responce
    }

}