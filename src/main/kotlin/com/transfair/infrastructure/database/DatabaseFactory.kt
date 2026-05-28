package com.transfair.infrastructure.database

import com.transfair.config.ConfigurationProvider
import com.transfair.infrastructure.database.tables.FilesTable
import org.jetbrains.exposed.v1.core.exposedLogger
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File

object DatabaseFactory {

    private var db: Database? = null;

    val database: Database
        get() = db ?: throw IllegalStateException("Database is not initialized.")

    fun initSqlite(): Database? {
        val config = ConfigurationProvider.sqliteConfiguration
        val dataFolder = File("data")
        if (!dataFolder.exists()) dataFolder.mkdirs()
        if (!File("data/${config.fileName}").exists()) File("data/${config.fileName}").createNewFile()

        val db = Database.connect(config.url)
        createTables(db)
        this.db = db
        exposedLogger.info("Sqlite database initialized.")
        return this.db
    }

    private fun createTables(db: Database) {
        transaction(db) {
            SchemaUtils.create(FilesTable)
        }
    }
}