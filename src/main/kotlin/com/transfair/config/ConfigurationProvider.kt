package com.transfair.config

import com.transfair.config.database.SqliteConfiguration

/**
 * Initialize configuration and provide it to the application.
 */
object ConfigurationProvider {

    // Token

    private var _tokenConfiguration: TokenConfiguration? = null

    // Storage

    private var _storageConfiguration: StorageConfiguration? = null

    // Database
    private var _databaseConfiguration: DatabaseConfiguration? = null

    private var _sqliteConfiguration: SqliteConfiguration? = null


    // Getters

    val tokenConfiguration: TokenConfiguration
        get() = _tokenConfiguration ?: throw IllegalStateException("Token configuration is not initialized.")

    val storageConfiguration: StorageConfiguration
        get() = _storageConfiguration ?: throw IllegalStateException("Storage configuration is not initialized.")

    val databaseConfiguration: DatabaseConfiguration
        get() = _databaseConfiguration ?: throw IllegalStateException("Database configuration is not initialized.")

    val sqliteConfiguration: SqliteConfiguration
        get() = _sqliteConfiguration ?: throw IllegalStateException("Sqlite configuration is not initialized.")

    fun <T> initialize(config: T) {
        when (config) {
            is TokenConfiguration -> _tokenConfiguration = config

            is StorageConfiguration -> _storageConfiguration = config

            is DatabaseConfiguration -> _databaseConfiguration = config
            is SqliteConfiguration -> _sqliteConfiguration = config
        }
    }

    fun <T> initialize(configs: List<T>) {
        configs.forEach { initialize(it) }
    }
}