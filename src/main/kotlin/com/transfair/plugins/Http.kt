package com.transfair.plugins

import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.partialcontent.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureHttp() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }
    install(PartialContent) {
        // Maximum number of ranges that will be accepted from a HTTP request.
        maxRangeCount = 10
    }
    routing {
        swaggerUI(path = "openapi") {
        }
    }
}
