package com.transfair.plugins

import com.transfair.domain.exceptions.FunctionalException
import com.transfair.domain.exceptions.TechnicalException
import com.transfair.routing.dto.output.ErrorDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {

        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorDto(500, cause.toString()))
        }

        exception<TechnicalException> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorDto(500, cause.toString()))
        }

        exception<FunctionalException> { call, cause ->
            call.respond(cause.status, ErrorDto(cause.status.value, cause.message))
        }

        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorDto(400, cause.message ?: "Bad request"))
        }
    }
}
