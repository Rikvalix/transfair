package com.transfair.plugins

import com.transfair.domain.ports.StorageService
import com.transfair.domain.services.FileTransferService
import com.transfair.routing.uploadRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    transferService: FileTransferService,
) {
    routing {
        uploadRoutes(transferService)
    }
}