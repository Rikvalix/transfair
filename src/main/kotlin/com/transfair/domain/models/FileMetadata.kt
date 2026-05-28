package com.transfair.domain.models

import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class FileMetadata(
    val uuid: UUID,
    val fileName: String,
    val size: Long,
    val createdAt: LocalDateTime,
    val expireAt: LocalDateTime,
    val maxDownloads: Int?,
    val currentDownloads: Int = 0
    ) {}