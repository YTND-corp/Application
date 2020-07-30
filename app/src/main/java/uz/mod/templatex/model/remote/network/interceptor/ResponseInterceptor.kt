package uz.mod.templatex.model.remote.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import uz.mod.templatex.utils.AppNewVersionAvailableException
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.ServerFailException

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        when (response.code()) {
            Const.API_SERVER_FAIL_STATUS_CODE -> throw ServerFailException()
            Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> throw AppNewVersionAvailableException()
        }
        return response
    }

}