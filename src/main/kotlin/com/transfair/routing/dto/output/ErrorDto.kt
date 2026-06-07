package com.transfair.routing.dto.output

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val code: Int,
    val message: String
) {

}