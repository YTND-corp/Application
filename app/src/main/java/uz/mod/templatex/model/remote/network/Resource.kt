package uz.mod.templatex.model.remote.network

import uz.mod.templatex.utils.is500

class Resource<T> constructor(
    val status: Status,
    var data: T? = null,
    val error: ApiError? = null
) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data)
        }

        fun <T> error(error: ApiError, data: T?): Resource<T> {
            return Resource(status = Status.ERROR, data = data, error = error.is500())
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data)
        }
    }

}