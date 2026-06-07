package com.transfair.routing.routes

import com.transfair.domain.services.FileTransferService
import com.transfair.routing.validator.validateFileId
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

fun Route.downloadRoutes(
    transferService: FileTransferService
) {

    get("/api/v1/download/{fileId}") {
        val id = call.parameters["fileId"]
        validateFileId.validate(id)

        val file = transferService.getFile(UUID.fromString(id))
        val metadata = transferService.getFileMetadata(UUID.fromString(id))

        call.response.header(
            HttpHeaders.ContentDisposition,
            ContentDisposition.Attachment.withParameter(
                ContentDisposition.Parameters.FileName,
                metadata.fileName
            )
                .toString()
        )
        call.respondOutputStream(
            contentType = ContentType.Application.OctetStream,
            status = HttpStatusCode.OK
        ) {
            withContext(Dispatchers.IO) {
                file.use { it.copyTo(this@respondOutputStream) }
            }
        }
    }
}