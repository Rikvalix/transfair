package com.transfair

import com.transfair.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureConfiguration()
    configureHttp()
    configureSerialization()
    configureStatusPages()
    configureRequestValidation()

    val storageService = configureStorage()

    configureRouting(storageService)

}