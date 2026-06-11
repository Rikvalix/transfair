package com.transfair.routing.routes

import com.transfair.domain.services.FileTransferService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.downloadRoutes(
    transferService: FileTransferService
) {

    get("/api/v1/download/{fileId}") {
        call.respond(HttpStatusCode.NotImplemented)
    }
}