package com.transfair

import com.transfair.domain.services.FileTransferService
import com.transfair.infrastructure.database.sqlite.FileRepository
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
    configureDatabase()
    val storageService = configureStorage()

    val fileTransferService = FileTransferService(
        FileRepository(), storageService
    )

    configureRouting(fileTransferService)

}