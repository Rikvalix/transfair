package com.transfair.plugins

import com.transfair.config.ConfigurationProvider
import com.transfair.config.DatabaseConfiguration
import com.transfair.config.StorageConfiguration
import com.transfair.config.database.SqliteConfiguration
import com.transfair.infrastructure.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import java.io.ObjectInputFilter

fun Application.configureConfiguration() {

    val config = environment.config;

    // Storage
    val storageConfig = StorageConfiguration(
        type = config.tryGetString("storage.type") ?: "local",
        expiration = config.tryGetString("storage.expiration")?.toLong() ?: 3600
    )
    ConfigurationProvider.initialize(storageConfig)

    // Database
    val databaseType = config.tryGetString("database.type") ?: "sqlite"

    when (databaseType) {
        "sqlite" -> ConfigurationProvider.initialize(
            listOf(
                DatabaseConfiguration(
                    type = "sqlite"
                ),
                SqliteConfiguration(
                    url = config.tryGetString("database.url") ?: "jdbc:sqlite:data/tranfair.db",
                    fileName = config.tryGetString("database.fileName") ?: "tranfair.db"
                )
            )
        )

        else -> {
            throw IllegalStateException("Database type $databaseType not supported.")
        }
    }
}