package com.transfair.routing.dto.input

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UploadFileFormDto(
    // Required
    val fileName: String,
    val fileType: String,
    val fileSize: Long,
    val checkSum: String,

    // Optional
    val expiration: LocalDateTime? = null,
    val maxDownloads: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val password: String? = null,
) {
}



