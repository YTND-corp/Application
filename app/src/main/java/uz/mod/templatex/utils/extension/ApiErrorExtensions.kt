package uz.mod.templatex.utils.extension

import uz.mod.templatex.model.remote.network.ApiError

private const val CODE_INTERNAL_SERVER_ERROR = 500

fun ApiError.is500(): ApiError {
    return if (code == CODE_INTERNAL_SERVER_ERROR) {
        this.copy(message = "Internal Server Error")
    } else {
        this
    }
}
