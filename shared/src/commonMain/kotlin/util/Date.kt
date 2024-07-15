package util

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

class LocalDateTimeHelper(
    private val currentDateTimeProvider: () -> Instant = DefaultCurrentDateTimeProvider,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault()
) {

    fun toHumanReadableTime(
        dateTime: LocalDateTime
    ): String {
        val now = currentDateTimeProvider()
        val nowLocalDateTime = now.toLocalDateTime(timeZone)
        val duration: Duration = now - dateTime.toInstant(timeZone)
        val days = duration.inWholeDays
        return when {
            duration.inWholeSeconds < SECONDS_IN_MINUTE -> "few secs ago"
            duration.inWholeMinutes == 1L -> "a minute ago"
            duration.inWholeMinutes < 10 -> "a few minutes ago"
            duration.inWholeMinutes < MINUTES_IN_HOUR -> "${duration.inWholeMinutes} minutes ago"
            duration.inWholeHours == 1L -> "an hour ago"
            isYesterday(dateTime.date) -> "Yesterday at ${dateTime.time.toShortTimeString()}"

            duration.inWholeHours < HOURS_IN_DAY -> "${duration.inWholeHours} hrs ago"
            dateTime.date.isSameWeekAs(nowLocalDateTime.date) ->
                "${dateTime.date.dayOfWeek()} at ${dateTime.time.toShortTimeString()}"

            days < DAYS_IN_WEEK -> "$days days ago"
            days < DAYS_IN_MONTH -> "${days / DAYS_IN_WEEK} weeks ago"
            days < DAYS_IN_YEAR -> "${days / DAYS_IN_MONTH} months ago"
            else -> "${days / DAYS_IN_YEAR} years ago"
        }
    }

    fun isYesterday(dateTime: LocalDate): Boolean {
        return dateTime.isYesterday(timeZone, currentDateTimeProvider)
    }

    companion object {
        private val DefaultCurrentDateTimeProvider = { Clock.System.now() }
        private const val SECONDS_IN_MINUTE = 60
        private const val MINUTES_IN_HOUR = 60
        private const val HOURS_IN_DAY = 24
        private const val DAYS_IN_WEEK = 7
        private const val DAYS_IN_MONTH = 30
        private const val DAYS_IN_YEAR = 365
    }
}

fun LocalDate.isYesterday(
    timeZone: TimeZone,
    currentDateTimeProvider: () -> Instant,
): Boolean {
    val now = currentDateTimeProvider().toLocalDateTime(timeZone).date
    val yesterday = now.minus(DatePeriod(days = 1))
    return this == yesterday
}

fun LocalDate.dayOfWeek(): String {
    return this.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
}

fun LocalDate.atStartOfWeek(): LocalDate {
    return this.minus(this.dayOfWeek.ordinal, DateTimeUnit.DAY)
}

fun LocalDate.isSameWeekAs(other: LocalDate): Boolean {
    return this.atStartOfWeek() == other.atStartOfWeek()
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun LocalTime.toShortTimeString(): String {
    return this.format(LocalTime.Format {
        byUnicodePattern("HH:mm")
    })
}