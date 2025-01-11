package com.jarvis.chiefcodetest.core.util


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    private const val FORMAT_PATTERN_ISO_DATE = "yyyy-MM-dd"

    fun String.parseISODate(): Date? {
        return try {
            val formatter = SimpleDateFormat(FORMAT_PATTERN_ISO_DATE, Locale.US)
            formatter.parse(this)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}