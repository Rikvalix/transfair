package com.transfair.config

/**
 * Initialize configuration and provide it to the application.
 */
object ConfigurationProvider {

    private var _storageConfiguration: StorageConfiguration? = null

    val storageConfiguration: StorageConfiguration
        get() = _storageConfiguration ?: throw IllegalStateException("Storage configuration is not initialized.")

    fun initialize(storage: StorageConfiguration) {
        if (_storageConfiguration == null) {
            _storageConfiguration = storage
        }
    }
}