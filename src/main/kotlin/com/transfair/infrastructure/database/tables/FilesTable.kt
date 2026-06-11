package com.transfair.infrastructure.database.tables

import com.transfair.utils.now
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.datetime

object FilesTable : UUIDTable("files") {

    val fileName = varchar("file_name", 255)
    val fileSize = long("size")
    val fileType = varchar("file_type", 255)
    val checkSum = varchar("checksum", 255)
    val password = varchar("", 255).nullable()
    val title = varchar("title", 255).nullable()
    val description = varchar("description", 255).nullable()
    val maxDownloads = integer("max_downloads")
    val currentDownloads = integer("current_downloads").default(0)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val expireAt = datetime("expire_at")
}