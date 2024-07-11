package util

import kotlinx.datetime.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration

class DateUtilTest {

    private val timeZone = TimeZone.currentSystemDefault()
    private lateinit var now: Instant

    @BeforeTest
    fun setUp() {
        now = Clock.System.now()
    }

    private fun beforeNow(value: Long, unit: DateTimeUnit.TimeBased): LocalDateTime {
        return now.minus(value, unit).toLocalDateTime(timeZone)
    }

    @Test
    fun testToHumanReadableTimeFewSecondsAgo() {
        val pastDateTime = beforeNow(5, DateTimeUnit.SECOND)
        val result = pastDateTime.toHumanReadableTime()
        assertEquals("few secs ago", result)
    }

    @Test
    fun testToHumanReadableTimeAMinuteAgo() {
        val pastDateTime = beforeNow(1, DateTimeUnit.MINUTE)
        val result = pastDateTime.toHumanReadableTime()
        assertEquals("a minute ago", result)
    }

    @Test
    fun testToHumanReadableTimeFewMinutesAgo() {
        val pastDateTime =beforeNow(9, DateTimeUnit.MINUTE)
        val result = pastDateTime.toHumanReadableTime()
        assertEquals("a few minutes ago", result)
    }

    @Test
    fun testToHumanReadableTimeNMinutesAgo() {
        val pastDateTime = beforeNow(20, DateTimeUnit.MINUTE)
        val result = pastDateTime.toHumanReadableTime()
        assertEquals("20 minutes ago", result)
    }

    @Test
    fun testToHumanReadableTimeAnHourAgo() {
        val pastDateTime = beforeNow(1, DateTimeUnit.HOUR)
        val result = pastDateTime.toHumanReadableTime()
        assertEquals("an hour ago", result)
    }

    @Test
    fun testToHumanReadableTimeBeforeMidNightIsYesterday() {
        val nowHour = now.toLocalDateTime(timeZone).hour
        val pastDateTime = beforeNow(nowHour + 1L, DateTimeUnit.HOUR)
        val result = pastDateTime.toHumanReadableTime()
        assertEquals("Yesterday at ${pastDateTime.time.shortTime()}", result)
    }

    @Test
    fun testToHumanReadableTimeThisWeek() {
        val pastDateTime = now.minus(Duration.parse("P3D"))
            .toLocalDateTime(timeZone)
        val result = pastDateTime.toHumanReadableTime()

        assertEquals("${pastDateTime.date.dayOfWeek()} at ${pastDateTime.time.shortTime()}", result)
    }

    @Test
    fun testToHumanReadableTimeWeeksAgo() {
        val pastDateTime = now.minus(Duration.parse("P20D"))
            .toLocalDateTime(timeZone)
        val result = pastDateTime.toHumanReadableTime()
        assertEquals("2 weeks ago", result)
    }
}