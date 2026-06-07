package com.transfair.routing.validator

import io.ktor.server.plugins.requestvalidation.*


fun interface Validator<Object> {

    fun validate(schema: Object): ValidationResult
}