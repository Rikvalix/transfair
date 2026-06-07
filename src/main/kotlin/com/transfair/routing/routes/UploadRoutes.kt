package com.transfair.routing.routes

import com.transfair.domain.services.FileTransferService
import com.transfair.routing.dto.input.UploadFileFormDto
import com.transfair.routing.dto.output.FileResponseDto
import com.transfair.routing.validator.validateFileInputDto
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*

fun Route.uploadRoutes(
    transferService: FileTransferService
) {

    post("/api/v1/upload") {
        val multipartData = call.receiveMultipart(formFieldLimit = 1024 * 1024 * 100)
        val properties = mutableMapOf<String, String>()
        var input: ByteReadChannel? = null
        val contentLength: String? = call.request.header(HttpHeaders.ContentLength)
        properties["contentLength"] = contentLength.toString()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name != null) {
                        properties[part.name!!] = part.value
                    }
                }

                is PartData.FileItem -> {
                    properties["fileName"] = part.originalFileName ?: ""
                    input = part.provider()
                }

                else -> {}
            }
            val form = UploadFileFormDto.fromMap(properties)
            validateFileInputDto.validate(form)

            val response = transferService.uploadFile(
                form,
                input!!
            )
            call.respond(
                HttpStatusCode.Created,
                FileResponseDto(
                    id = response.uuid.toString(),
                    expiresAt = response.expireAt
                )
            )
            part.release()
        }
    }
}