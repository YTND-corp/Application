package uz.mod.templatex.utils.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import android.text.Html
import android.os.Build
import android.text.Spanned


val String.clear: String
    get() {
        return this.replace("-".toRegex(), "")
            .replace(" ".toRegex(), "")
            .trim()
    }

fun String.convertToDate(format: String): Date? {
    var dateStr: Date? = null
    val df = SimpleDateFormat(format, Locale.ROOT)
    try {
        dateStr = df.parse(this)
    } catch (ex: Exception) {

    }
    return dateStr
}

fun String.phoneFormat(): String? {
    val text = this.clear
    if (text.length == 9) {
        return "+998" + text.substring(0, 2) + " " + text.substring(2, 5) + " " + text.substring(5, 7) + " " + text.substring(
            7,
            9
        )
    }
    return text
}

fun String.backEndPhoneFormat(): String? {
    val text = this.clear
    return if (text.length == 12) {
        "+${text}" //+ text.substring(0, 3) + " " + text.substring(3, 5) + " " + text.substring(5, 8) + "-" + text.substring(8, 10) + "-" + text.substring(10)
    } else text
}

fun String.plateFormat(): String? {
    val text = this.clear
    var customPlate = true

    if (text.length >= 4) {
        customPlate = !text[2].isDigit()
    }

    if (text.length == 8) {

        if (customPlate)
            return text.substring(0, 2) + " " + text.substring(2, 3) + " " + text.substring(3, 6) + " " + text.substring(6)
        else {
            return text.substring(0, 2) + " " + text.substring(2, 5) + " " + text.substring(5, 8)
        }
    }
    return text
}

fun String.digitsOnly(): String {
    return this.replace("[^\\d]".toRegex(), "")
}

fun moneyFormat(value: Long): String {
    return DecimalFormat.getInstance().format(value)
}

fun moneyFormat(value: Double): String {
    return DecimalFormat.getInstance().format(value)
}

fun Any.roundRating(): String {
    val symbols = DecimalFormatSymbols(Locale.US)
    return DecimalFormat("#.#", symbols).format(this)
}

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}