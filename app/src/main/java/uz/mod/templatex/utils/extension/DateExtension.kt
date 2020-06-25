package uz.mod.templatex.utils.extension

import uz.mod.templatex.utils.LanguageHelper
import java.text.SimpleDateFormat
import java.util.*

fun Date.convertTo(format: String): String? {
    var dateStr: String? = null
    val df = SimpleDateFormat(format, LanguageHelper.getLocale())
    try {
        dateStr = df.format(this)
    } catch (ex: Exception) {

    }
    return dateStr
}

var Date.calendar: Calendar
    get() = Calendar.getInstance().apply { time = this@calendar }
    set(value) {
        time = value.timeInMillis
    }



val Date.isFuture get() = this > Date()

val Date.isPast get() = this < Date()



val Date.isToday: Boolean
    get() {
        val calToday = Calendar.getInstance()
        return calToday.get(Calendar.ERA) == calendar.get(Calendar.ERA) &&
                calToday.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                calToday.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
    }


val Date.isYesterday: Boolean
    get() {
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        return tomorrow.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                tomorrow.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
    }

val Date.isTomorrow: Boolean
    get() {
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
        return tomorrow.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                tomorrow.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
    }

fun Date.today(): Date {
    return Date()
}

fun Date.yesterday(): Date {
    val cal = calendar
    cal.add(Calendar.DAY_OF_YEAR, -1)
    return cal.time
}

fun Date.tomorrow(): Date {
    val cal = calendar
    cal.add(Calendar.DAY_OF_YEAR, 1)
    return cal.time
}

fun Date.hour(): Int {
    return calendar.get(Calendar.HOUR)
}

fun Date.minute(): Int {
    return calendar.get(Calendar.MINUTE)
}

fun Date.second(): Int {
    return calendar.get(Calendar.SECOND)
}

fun Date.month(): Int {
    return calendar.get(Calendar.MONTH) + 1
}

fun Date.year(): Int {
    return calendar.get(Calendar.YEAR)
}

fun Date.day(): Int {
    return calendar.get(Calendar.DAY_OF_MONTH)
}

fun Date.dayOfWeek(): Int {
    return calendar.get(Calendar.DAY_OF_WEEK)
}

fun Date.dayOfWeekName(locale: Locale? = Locale.getDefault()): String {
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)
}

fun Date.dayOfYear(): Int {
    return calendar.get(Calendar.DAY_OF_YEAR)
}

