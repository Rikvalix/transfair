package com.transfair.routing.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class FileResponseDto(
    val id: String,
    val expiresAt: LocalDateTime
)
