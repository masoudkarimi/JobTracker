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
    val seconds = duration.inWholeSeconds
    val minutes = duration.inWholeMinutes
    val hours = duration.inWholeHours
    val days = duration.inWholeDays

    return when {
        seconds < 60 -> "few secs ago"
        minutes == 1L -> "a minute ago"
        minutes < 10 -> "a few minutes ago"
        minutes < 60 -> "$minutes minutes ago"
        hours == 1L -> "an hour ago"
        isYesterday() -> "Yesterday at ${this.time.shortTime()}"
        hours < 24 -> "$hours hrs ago"
        this.isSameWeekAs(now.toLocalDateTime(timeZone)) -> "${
            this.date.dayOfWeek()
        } at ${this.time.shortTime()}"

        days < 30 -> "${days / 7} weeks ago"
        days < 365 -> "${days / 30} months ago"
        else -> "${days / 365} years ago"
    }
}

fun LocalDate.dayOfWeek() : String {
    return this.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun LocalTime.shortTime(): String {
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