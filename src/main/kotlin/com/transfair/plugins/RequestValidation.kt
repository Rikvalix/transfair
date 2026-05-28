package com.transfair.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureRequestValidation() {
    install(RequestValidation) {

    }
}
