package com.edorin.litunyi.ext

/**
 * Convert seconds to milliseconds
 */
fun Long.secondsToMillis(): Long = this * 1000

/**
 * Convert minutes to milliseconds
 */
fun Long.minutesToMillis(): Long = secondsToMillis() * 60

/**
 * Convert hours to milliseconds
 */
fun Long.hoursToMillis(): Long = minutesToMillis() * 60

/**
 * Convert milliseconds to elapsed time string
 */
fun Long.millisToReadableElapsedTime(): String =
    try {
        val s = this / 1000
        val m = s / 60
        val h = m / 60
        val d = h / 24

        if (m < 1) "$s second${if (s == 1.toLong()) "" else "s"} ago"
        if (h < 1) "$m minute${if (m == 1.toLong()) "" else "s"} ago"
        if (d < 1) "$h hour${if (h == 1.toLong()) "" else "s"}, ${(m % 60)} minute${if ((m % 60) == 1.toLong()) "" else "s"} ago"
        "$d day${if (d == 1.toLong()) "" else "s"}, ${(h % 24)} hour${if ((h % 24) == 1.toLong()) "" else "s"}, ${(m % 60)} minute${if ((m % 60) == 1.toLong()) "" else "s"} ago"
    } catch (e: Exception) {
        this.toString()
    }
