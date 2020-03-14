package uz.uzmobile.templatex.model.remote.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import timber.log.Timber

abstract class NetworkOnlyResource<ResultType, RequestType>
@MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>() //List<Repo>
    private val request = MediatorLiveData<Resource<RequestType>>() //RepoSearchResponse

    init {
        result.postValue(Resource.loading(null))
        fetchFromNetwork()
    }

    @MainThread
    private fun setResultValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            response?.let {
                if (response.isSuccessful) {

                    val requestType = processResponse(response)
                    val resultType = processResult(requestType)

                    setResultValue(Resource.success(resultType))

                } else {
                    val requestType = processResponse(response)
                    val resultType = processResult(requestType)
                    Timber.e("NetworkOnlyResource = ${response.message}")

                    if (response.message!=null) {
                        setResultValue(Resource.error(response.message, resultType))
                    } else {
                        setResultValue(Resource.error(ApiError(0,null,"Unknown Error"), resultType))
                    }
                    onFetchFailed()
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun processResult(item: RequestType?): ResultType?

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}