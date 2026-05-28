package com.transfair.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

fun LocalDateTime.plusSeconds(seconds: Long): LocalDateTime {
    val timeZone = TimeZone.currentSystemDefault()
    return this.toInstant(timeZone).plus(seconds.seconds).toLocalDateTime(timeZone)
}