package com.transfair.plugins

import com.transfair.domain.services.FileTransferService
import com.transfair.routing.routes.downloadRoutes
import com.transfair.routing.routes.uploadRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    transferService: FileTransferService,
) {
    routing {
        uploadRoutes(transferService)
        downloadRoutes(transferService)

        // Frontend
        singlePageApplication() {
            vue("frontend")
        }
    }
}