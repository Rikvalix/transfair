package com.transfair.domain.models

import java.util.UUID

data class FileMetadata(
    val uuid: UUID,
    val fileName: String,
    val size: Long,
    val createdAt: Long,
    val expireAt: Long,
    val maxDownloads: Int?,
    val currentDownloads: Int = 0
    ) {}