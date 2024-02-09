package com.example.neoticket.Utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private val russianMonths = arrayOf(
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
        )

        private val russianMonthsShort = arrayOf(
            "янв", "фев", "мар", "апр", "май", "июн",
            "июл", "авг", "сен", "окт", "ноя", "дек"
        )

        fun formatRussianDate(dateString: String?, format: String): String {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = dateFormat.parse(dateString)

            val calendar = Calendar.getInstance()
            calendar.time = date

            return "${calendar.get(Calendar.DAY_OF_MONTH)} ${russianMonths[calendar.get(Calendar.MONTH)]}"
        }
        fun getYearFromDate(dateString: String?, format: String): String {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = dateFormat.parse(dateString)

            val calendar = Calendar.getInstance()
            calendar.time = date

            return "${calendar.get(Calendar.YEAR)}"
        }

        fun formatRussianShortDate(dateString: String?, format: String): String {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = dateFormat.parse(dateString)

            val calendar = Calendar.getInstance()
            calendar.time = date

            return "${calendar.get(Calendar.DAY_OF_MONTH)} ${russianMonthsShort[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}"
        }
    }
}
