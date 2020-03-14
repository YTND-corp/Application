package uz.uzmobile.templatex.utils


object Const {
    val DB_NAME = "UzAutoDatabase"

    val BASE_URL = "https://dealer.uzavtosanoat.uz/"

    val privacyPolicy="https://www.aqlify.uz/terms"
    val publicOffer="https://www.aqlify.uz/terms"

    val CODE_TIME_OUT: Long = 2 * 60000
}

object RequestCode {
    const val POP = 100
}

object ResultCode {
    const val BOOKED_SUCCESS = 100
    const val BOOKED_ERROR = 101

    const val OFFER_SUCCESS= 102
    const val EDIT_TRIP_SUCCESS= 103
}

object Perm {
    const val CAMERA: Int = 123
    const val GALLERY: Int = 124
    const val LOCATION: Int = 125
}