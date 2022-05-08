package com.example.pocketfridge.utility

import com.example.pocketfridge.view.fragment.IngredientFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日付関連ユーティリティクラス.
 */
class DateUtil {

    companion object {

       private const val DATE_FORMAT = "yyyy/MM/dd"
    }

    private val format = SimpleDateFormat(DATE_FORMAT, Locale.JAPAN)

    fun stringToDate(dateStr: String?): Date? {
        var date: Date? = null
        try {
            dateStr?.let { date = format.parse(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun dateToString(date: Date): String? {
        var string: String? = null
        try {
            string = format.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return string
    }

    fun todayString() : String {
      return dateToString(Date()) ?: "2022/01/01"
    }
}