package com.transfair.plugins

import com.transfair.config.ConfigurationProvider
import com.transfair.infrastructure.database.DatabaseFactory
import io.ktor.server.application.Application
import io.ktor.server.application.log
import org.jetbrains.exposed.v1.jdbc.Database


fun Application.configureDatabase(): Database? {
    return when (val databaseType = ConfigurationProvider.databaseConfiguration.type) {
        "sqlite" -> DatabaseFactory.initSqlite()
        else -> {
            log.warn("Database type $databaseType not supported, sqlite will be used")
            DatabaseFactory.initSqlite()
        }
    }
}