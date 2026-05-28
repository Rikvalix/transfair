package com.transfair.routing

import com.transfair.config.ConfigurationProvider
import com.transfair.domain.ports.StorageService
import com.transfair.routing.dto.FileResponseDto
import com.transfair.utils.now
import com.transfair.utils.plusSeconds
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.engine.ShutDownUrl
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*
import kotlin.time.Clock

fun Route.uploadRoutes(
    storageService: StorageService
) {

    post("/api/v1/upload") {
        val uuid = UUID.randomUUID()
        val multipartData = call.receiveMultipart(formFieldLimit = 1024 * 1024 * 100)
        val properties = mutableMapOf<String, String>()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name != null) {
                        properties[part.name!!] = part.value;
                    }
                }
                is PartData.FileItem -> {
                    val input = part.provider().toInputStream()
                    storageService.saveFile(uuid, input)
                }
                else -> {}
            }
            part.release()
        }
        call.respond(
            HttpStatusCode.Created,
            FileResponseDto(
                id = uuid.toString(),
                expiresAt = LocalDateTime.now().plusSeconds(ConfigurationProvider.storageConfiguration.expiration)
            )
        )
    }
}