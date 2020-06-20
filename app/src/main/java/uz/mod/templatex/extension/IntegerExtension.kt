package uz.mod.templatex.extension

import android.content.res.Resources
import java.text.DecimalFormat

fun Int.moneyFormat(): String {
    return DecimalFormat.getInstance().format(this)
}

fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun Int.toDp() = (this / Resources.getSystem().displayMetrics.density).toInt()