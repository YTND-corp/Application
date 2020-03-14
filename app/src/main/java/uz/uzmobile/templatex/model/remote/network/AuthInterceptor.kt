package uz.uzmobile.templatex.model.remote.network

import okhttp3.Interceptor
import okhttp3.Response
class AuthInterceptor constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        var url = req.url()

//        Timber.e("URL = ${url}")
        var reqBuilder = req.newBuilder()
            .addHeader("Content-Type", "application/json")
//            .addHeader("lang_code",prefs.selectedLanguage?.name?:"ru_RU")
//           .url(url)
//
//        if (Prefs.token != null) {
//            reqBuilder.addHeader("Authorization", "Bearer " + Prefs.token)
//        }
//        val versionName = BuildConfig.VERSION_NAME
//        reqBuilder.addHeader("version", "a${versionName}")

        req = reqBuilder.build()
        val responce: Response = chain.proceed(req)
        return responce
    }

}