package uz.uzmobile.templatex.model.remote.network

class Resource<T> constructor(val status: Status, var data: T? = null, val error: ApiError? = null) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data)
        }

        fun <T> error(msg: ApiError, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data)
        }
    }

}