package uz.mod.templatex.model.remote.network

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import uz.mod.templatex.utils.NoConnectionException
import uz.mod.templatex.utils.ServerFailException
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            when (throwable) {
                                is NoConnectionException -> postValue(ApiResponse(throwable))
                                is ServerFailException -> postValue(ApiResponse(throwable))
                                else -> postValue(ApiResponse(throwable))
                            }
                        }
                    })
                }
            }
        }
    }
}