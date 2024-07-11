package util

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
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

fun LocalDateTime.toHumanReadableTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val now = Clock.System.now()
    val duration: Duration = now - this.toInstant(timeZone)
    val days = duration.inWholeDays

    return when {
        duration.inWholeSeconds < SECONDS_IN_MINUTE -> "few secs ago"
        duration.inWholeMinutes == 1L -> "a minute ago"
        duration.inWholeMinutes < 10 -> "a few minutes ago"
        duration.inWholeMinutes < MINUTES_IN_HOUR -> "${duration.inWholeMinutes} minutes ago"
        duration.inWholeHours == 1L -> "an hour ago"
        isYesterday() -> "Yesterday at ${this.time.toShortTimeString()}"
        duration.inWholeHours < HOURS_IN_DAY -> "${duration.inWholeHours} hrs ago"
        this.isSameWeekAs(now.toLocalDateTime(timeZone)) ->
            "${this.date.dayOfWeek()} at ${this.time.toShortTimeString()}"
        days < DAYS_IN_MONTH -> "${days / DAYS_IN_WEEK} weeks ago"
        days < DAYS_IN_YEAR -> "${days / DAYS_IN_MONTH} months ago"
        else -> "${days / DAYS_IN_YEAR} years ago"
    }
}

fun LocalDate.dayOfWeek(): String {
    return this.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun LocalTime.toShortTimeString(): String {
    return this.format(LocalTime.Format {
        byUnicodePattern("HH:mm")
    })
}

fun LocalDateTime.isYesterday(): Boolean {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val yesterday = now.minus(DatePeriod(days = 1))
    return this.date == yesterday
}

fun LocalDateTime.isSameWeekAs(other: LocalDateTime): Boolean {
    return this.date.atStartOfWeek() == other.date.atStartOfWeek()
}

fun LocalDate.atStartOfWeek(): LocalDate {
    return this.minus(this.dayOfWeek.ordinal, DateTimeUnit.DAY)
}

private const val SECONDS_IN_MINUTE = 60
private const val MINUTES_IN_HOUR = 60
private const val HOURS_IN_DAY = 24
private const val DAYS_IN_WEEK = 7
private const val DAYS_IN_MONTH = 30
private const val DAYS_IN_YEAR = 365