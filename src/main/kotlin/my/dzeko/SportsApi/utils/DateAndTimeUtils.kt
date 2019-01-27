package my.dzeko.SportsApi.utils

import java.lang.Exception
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object DateAndTimeUtils {

    fun getDateFromString(dateStr :String) : LocalDateTime {
        val dayAndMonth = dateStr.split(" ")
        val dateTime = LocalDateTime.now()
        dateTime.withDayOfMonth(dayAndMonth[0].toInt())
        dateTime.withMonth(getMonthInt(dayAndMonth[1]))
        return dateTime
    }

    private fun getMonthInt(s: String): Int {
        return when(s.capitalize()) {
            "Января" -> 1
            "Февраля" -> 2
            "Марта" -> 3
            "Апреля" -> 4
            "Мая" -> 5
            "Июня" -> 6
            "Июля" -> 7
            "Августа" -> 8
            "Сентября" -> 9
            "Октября" -> 10
            "Ноября" -> 11
            "Декабря" -> 12
            else -> throw Exception("Wrong month")
        }
    }

    fun getDateWithTime(timeStr: String, localDateTime: LocalDateTime): LocalDateTime {
        val hoursAndMinutes = timeStr.split(":")
        return localDateTime.apply {
            withHour(hoursAndMinutes[0].toInt())
            withMinute(hoursAndMinutes[1].toInt())
        }
    }

    fun convertLocalDateTimeToDate(localDateTime: LocalDateTime) :Date {
       return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}