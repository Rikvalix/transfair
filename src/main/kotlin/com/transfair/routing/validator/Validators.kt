package com.transfair.routing.validator

import com.transfair.routing.dto.input.UploadFileFormDto
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.toJavaLocalDateTime
import java.util.*

val validateFileInputDto = Validator<UploadFileFormDto> { dto ->
    when {
        dto.fileName.isEmpty() -> ValidationResult.Invalid("fileName should not be empty")
        dto.fileType.isEmpty() -> ValidationResult.Invalid("fileType should not be empty")
        dto.checkSum.isEmpty() -> ValidationResult.Invalid("checkSum should not be empty")
        dto.fileSize <= 0 -> ValidationResult.Invalid("size should be greater than 0")
        dto.expiration != null && dto.expiration.toJavaLocalDateTime()
            .isBefore(java.time.LocalDateTime.now()) -> ValidationResult.Invalid("expiration should be greater than now")

        dto.maxDownloads != null && dto.maxDownloads <= 0 -> ValidationResult.Invalid("maxDownloads should be greater than 0")
        else -> ValidationResult.Valid
    }
}

val validateFileId = Validator<String?> { id ->
    when {
        id == null -> ValidationResult.Invalid("id should not be null")
        id.isEmpty() -> ValidationResult.Invalid("id should not be empty")
        UUID.fromString(id) == null -> ValidationResult.Invalid("id should be a valid UUID")
        else -> ValidationResult.Valid
    }
}