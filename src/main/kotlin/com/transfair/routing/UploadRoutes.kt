package com.transfair.routing

import com.transfair.domain.services.FileTransferService
import com.transfair.routing.dto.FileResponseDto
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.jvm.javaio.*
import java.io.InputStream

fun Route.uploadRoutes(
    transferService: FileTransferService
) {

    post("/api/v1/upload") {
        val multipartData = call.receiveMultipart(formFieldLimit = 1024 * 1024 * 100)
        val properties = mutableMapOf<String, String>()
        var input: InputStream? = null

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name != null) {
                        properties[part.name!!] = part.value;
                    }
                }

                is PartData.FileItem -> {
                    input = part.provider().toInputStream()

                }

                else -> {}
            }
            part.release()
        }
        //TODO: Add verification of file size and fileName
        val response = transferService.uploadFile(properties["fileName"]!!, properties["size"]!!.toLong(), input!!)

        call.respond(
            HttpStatusCode.Created,
            FileResponseDto(
                id = response.uuid.toString(),
                expiresAt = response.expireAt
            )
        )
    }
}