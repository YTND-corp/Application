package uz.uzmobile.templatex.model.remote.network

import com.google.gson.Gson
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

@Suppress("MemberVisibilityCanBePrivate")
class ApiResponse<T> {
    val code: Int
    val body: T?
    val message: ApiError?

    val isSuccessful: Boolean
        get() = code in 200..300
    val isFailure: Boolean

    constructor(error: Throwable) {
        this.code = 500
        this.body = null
        this.message = ApiError(500,null, error.message?:"")
        this.isFailure = true
    }

    constructor(response: Response<T>) {
        this.code = response.code()

        if (response.isSuccessful) {
            this.body = response.body()
            this.message = null
            this.isFailure = false
        } else {
            var errorMessage: ApiError? = null

            response.errorBody()?.let {
                try {
                    val gson = Gson()
                    errorMessage = gson.fromJson(it.string(), ApiError::class.java)
                    Timber.e("ResponceBody = ${errorMessage.toString()}")
                } catch (e: Exception) {
                    errorMessage = ApiError(code,null,response.message())
                    Timber.e(response.message(), "error while parsing response")
                }
            }

            if (errorMessage?.code==0) {
                errorMessage = ApiError(code,null, response.message())
            }

            Timber.e("Final error = ${errorMessage.toString()}")
            this.body = null
            this.message = errorMessage
            this.isFailure = true
        }
    }
}