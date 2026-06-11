package com.transfair.routing.routes

import com.transfair.domain.services.FileTransferService
import com.transfair.routing.dto.input.UploadFileFormDto
import com.transfair.routing.validator.validateFileInputDto
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.uploadRoutes(
    transferService: FileTransferService
) {

    post("/api/v1/upload") {
        val form = call.receive<UploadFileFormDto>()

        validateFileInputDto.validate(form)
        val metadata = transferService.createMetadata(form)

        call.respond(HttpStatusCode.Created,metadata)
    }

    post("/api/v1/upload/{uploadId}") {

    }

    get("/api/v1/upload/{uploadId}") {

    }

    delete("/api/v1/upload/{uploadId}/finalize") {

    }
}