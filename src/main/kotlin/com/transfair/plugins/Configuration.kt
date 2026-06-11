package com.transfair.plugins

import com.transfair.config.ConfigurationProvider
import com.transfair.config.DatabaseConfiguration
import com.transfair.config.StorageConfiguration
import com.transfair.config.TokenConfiguration
import com.transfair.config.database.SqliteConfiguration
import io.ktor.server.application.*
import io.ktor.server.config.*

fun Application.configureConfiguration() {

    val config = environment.config

    // Token
    val tokenConfiguration = TokenConfiguration(
        secret = config.tryGetString("jwt.secret") ?: throw IllegalStateException("JWT secret is not set."),
    )

    // Storage
    val storageConfig = StorageConfiguration(
        type = config.tryGetString("storage.type") ?: "local",
        expiration = config.tryGetString("storage.expiration")?.toLong() ?: 3600
    )

    // Database
    val databaseType = config.tryGetString("database.type") ?: "sqlite"

    when (databaseType) {
        "sqlite" -> ConfigurationProvider.initialize(
            listOf(
                DatabaseConfiguration(
                    type = "sqlite"
                ),
                SqliteConfiguration(
                    url = config.tryGetString("database.url") ?: "jdbc:sqlite:data/transfair.db",
                    fileName = config.tryGetString("database.fileName") ?: "transfair.db"
                )
            )
        )

        else -> {
            throw IllegalStateException("Database type $databaseType not supported.")
        }
    }

    ConfigurationProvider.initialize(listOf(tokenConfiguration,storageConfig))
}