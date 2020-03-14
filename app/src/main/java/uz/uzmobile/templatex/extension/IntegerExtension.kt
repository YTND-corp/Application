package uz.uzmobile.templatex.extension

import java.text.DecimalFormat

fun Int.moneyFormat(): String {
    return DecimalFormat.getInstance().format(this)
}