package com.transfair.plugins

import com.transfair.config.ConfigurationProvider
import com.transfair.domain.ports.StorageService
import com.transfair.infrastructure.storage.LocalStorageService
import io.ktor.server.application.*

fun Application.configureStorage(): StorageService {
    return when (val storageType = ConfigurationProvider.storageConfiguration.type) {
        "local" -> LocalStorageService()

        else -> {
            log.warn("Storage type $storageType not supported, local storage will be used")
            LocalStorageService()
        }
    }
}