package com.transfair.routing.dto.output

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class FileResponseDto(
    val id: String,
    val expiresAt: LocalDateTime
)