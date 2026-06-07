package com.transfair.routing.dto.input

import com.transfair.utils.now
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UploadFileFormDto(
    val expiration: LocalDateTime? = null,
    val maxDownloads: Int? = null,
    val fileName: String,
    val size: Long
) {
    companion object {

        fun fromMap(map: Map<String, Any?>): UploadFileFormDto {
            return UploadFileFormDto(
                expiration = LocalDateTime.parse(map["expiration"] as String? ?: LocalDateTime.now().toString()),
                maxDownloads = (map["maxDownloads"] as String?)?.toInt(),
                fileName = map["fileName"] as String,
                size = (map["contentLength"] as String).toLong()
            )
        }
    }
}



