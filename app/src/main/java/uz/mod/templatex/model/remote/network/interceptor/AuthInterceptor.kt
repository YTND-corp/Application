package uz.mod.templatex.model.remote.network.interceptor

import android.content.Context
import android.provider.Settings
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import uz.aqlify.yonda.utils.Prefs
import uz.mod.templatex.BuildConfig

class AuthInterceptor constructor(val context: Context, val prefs: Prefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        var url = req.url()
        val mobuid = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.e("Mobile-UID: $mobuid")
        val reqBuilder = req.newBuilder()
            .addHeader("Content-Type", "application/json")
//            .addHeader("language",prefs.selectedLanguage?.name?:"ru")
            .addHeader("Mobile-UID", mobuid)
            .addHeader("X-App-Platform", "Android")
            .addHeader("X-App-Version", BuildConfig.VERSION_NAME)
//           .url(url)
        prefs.token?.let {
            reqBuilder.addHeader("Authorization", "Bearer $it")
        }
//        val versionName = BuildConfig.VERSION_NAME
//        reqBuilder.addHeader("version", "a${versionName}")

        req = reqBuilder.build()
        return chain.proceed(req)
    }

}