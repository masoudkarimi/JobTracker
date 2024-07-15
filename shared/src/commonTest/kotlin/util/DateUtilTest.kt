package util

import kotlinx.datetime.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilTest {

    // Test in standard timezone
    private val timeZone = TimeZone.UTC

    @Test
    fun testToHumanReadableTimeFewSecondsAgo() {
        val currentDateTimeProvider = { Instant.parse("2024-07-08T22:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-08T22:21:36.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )

        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("few secs ago", result)
    }

    @Test
    fun testToHumanReadableTimeAMinuteAgo() {
        val currentDateTimeProvider = { Instant.parse("2024-07-08T22:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-08T22:20:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("a minute ago", result)
    }

    @Test
    fun testToHumanReadableTimeFewMinutesAgo() {
        val currentDateTimeProvider = { Instant.parse("2024-07-08T22:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-08T22:12:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("a few minutes ago", result)
    }

    @Test
    fun testToHumanReadableTimeNMinutesAgo() {
        val currentDateTimeProvider = { Instant.parse("2024-07-08T22:45:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-08T22:10:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("35 minutes ago", result)
    }

    @Test
    fun testToHumanReadableTimeAnHourAgo() {
        val currentDateTimeProvider = { Instant.parse("2024-07-08T22:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-08T21:21:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("an hour ago", result)
    }

    @Test
    fun testToHumanReadable2HoursAgoInYesterday() {
        val currentDateTimeProvider = { Instant.parse("2024-07-08T00:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-07T22:21:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("Yesterday at 22:21", result)
    }

    @Test
    fun testToHumanReadableTimeBeforeMidNightIsYesterday() {
        val currentDateTimeProvider = { Instant.parse("2024-07-08T22:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-07T23:21:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("Yesterday at 23:21", result)
    }

    @Test
    fun testToHumanReadableTimeThisWeek() {
        // Current and past data are in same week
        val currentDateTimeProvider = { Instant.parse("2024-07-14T22:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-11T22:21:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)

        assertEquals(
            "${pastDateTime.date.dayOfWeek()} at ${
                pastDateTime.time.toShortTimeString()
            }",
            result
        )
    }

    @Test
    fun testToHumanReadableLastWeek() {
        // Current and past data are in different days
        // Monday
        val currentDateTimeProvider = { Instant.parse("2024-07-15T22:21:41.53Z") }
        // Thursday last week
        val pastDateTime = Instant.parse("2024-07-12T22:21:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )

        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)

        assertEquals(
            "3 days ago",
            result
        )
    }

    @Test
    fun testToHumanReadableTime20DaysAgoWillShow2WeeksAgo() {
        val currentDateTimeProvider = { Instant.parse("2024-07-27T22:21:41.53Z") }
        val pastDateTime = Instant.parse("2024-07-07T22:21:41.53Z").toLocalDateTime(timeZone)
        val dateTimeHelper = LocalDateTimeHelper(
            timeZone = timeZone,
            currentDateTimeProvider = currentDateTimeProvider
        )
        val result = dateTimeHelper.toHumanReadableTime(pastDateTime)
        assertEquals("2 weeks ago", result)
    }
}