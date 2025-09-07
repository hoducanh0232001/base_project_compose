package com.example.core.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTime {

    fun formatDate(date: Long, pattern: String, locale: Locale = Locale.US): String {
        val simpleDateFormat = SimpleDateFormat(pattern, locale)
        return simpleDateFormat.format(Date(date))
    }

    fun formatDate(date: Date, pattern: String, locale: Locale = Locale.US): String {
        val simpleDateFormat = SimpleDateFormat(pattern, locale)
        return simpleDateFormat.format(date)
    }

    fun parseDate(
        date: String,
        pattern: String,
        locale: Locale = Locale.US,
        timeZone: TimeZone = TimeZone.getDefault(),
    ): Date? = runCatching {
        val simpleDateFormat = SimpleDateFormat(pattern, locale)
        simpleDateFormat.timeZone = timeZone
        simpleDateFormat.parse(date)
    }.getOrNull()

    fun convert(
        date: String,
        fromPattern: String,
        toPattern: String,
        locale: Locale = Locale.US,
        timeZone: TimeZone = TimeZone.getDefault(),
    ): String {
        val fromDate = parseDate(date, fromPattern, locale, timeZone)
        return formatDate(fromDate ?: Date(), toPattern, locale)
    }

    fun convertDateStringToLong(dateString: String, format: String): Long {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = dateFormat.parse(dateString) ?: throw IllegalArgumentException("Invalid date format.")
        return date.time
    }

    fun convertMinuteToHour(min: Int): Double {
        return ((min * 6) / 60.0)
    }
}

object TimeFormat {
    const val D = "d"
    const val HHmm = "HHmm"
    const val HH_mm = "HH:mm"
    const val MM_DD = "MM/dd"
    const val YYYYMMDD = "yyyyMMdd"
    const val YYYY_MM_DD = "yyyy/MM/dd"
    const val YYYY_MM_DD_2 = "yyyy-MM-dd"
    const val YYYY_MM_DD_3 = "yyyy.MM.dd"
    const val YYYY_MM_DD_HH_MM_SLASH = "yyyy/MM/dd HH:mm"
    const val YYYY_MM_DD_HH_MM_DASH = "yyyy-MM-dd HH:mm"
    const val YYYY_MM_DD_HH_MM_DOT = "yyyy.MM.dd HH:mm"
    const val MM_DD_HH_MM = "MM/dd HH:mm"
    const val YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS"
    const val YYYYMMDDHHMMSS = "yyyyMMddHHmmss"
}

object TimeZones {
    val GMT: TimeZone get() = TimeZone.getTimeZone("GMT")
    val JAPAN: TimeZone get() = TimeZone.getTimeZone("GMT+9")
    val LOCAL: TimeZone get() = TimeZone.getDefault()
}
