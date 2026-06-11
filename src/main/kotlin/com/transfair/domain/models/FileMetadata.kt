package com.transfair.domain.models

import kotlinx.datetime.LocalDateTime
import java.util.*

data class FileMetadata(
    val uuid: UUID,
    val fileName: String,
    val fileSize: Long,
    val fileType: String,
    val checkSum: String,
    val password: String?,
    val title: String?,
    val description: String?,
    val maxDownloads: Int,
    val currentDownloads: Int = 0,
    val createdAt: LocalDateTime,
    val expireAt: LocalDateTime,
)