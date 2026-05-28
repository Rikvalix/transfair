package com.transfair.plugins

import com.transfair.config.ConfigurationProvider
import com.transfair.config.StorageConfiguration
import io.ktor.server.application.Application
import io.ktor.server.config.getAs
import io.ktor.server.config.tryGetString

fun Application.configureConfiguration() {

    val config = environment.config;

    // Storage
    val storageConfig = StorageConfiguration(
        type = config.tryGetString("storage.type") ?: "local",
        expiration = config.tryGetString("storage.expiration")?.toLong() ?: 3600
    )

    ConfigurationProvider.initialize(storageConfig)


}