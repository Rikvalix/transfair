package com.transfair.domain.exceptions

import io.ktor.http.*

class FunctionalException(
    val status: HttpStatusCode = HttpStatusCode.Conflict,
    override val message: String = "Business rule violation:",
) : Exception() {
}