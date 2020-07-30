package uz.mod.templatex.utils

import java.io.IOException

class NoConnectionException(errorMessage: String = "") : IOException(errorMessage)

class ServerFailException(errorMessage: String = "") : IOException(errorMessage)

class AppNewVersionAvailableException(errorMessage: String = "") : IOException(errorMessage)