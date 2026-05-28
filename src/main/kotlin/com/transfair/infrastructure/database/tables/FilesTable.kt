package com.transfair.infrastructure.database.tables

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.datetime

object FilesTable : UUIDTable("files") {
    val fileName = varchar("file_name", 255)
    val size = long("size")
    val createdAt = datetime("created_at")
    val expireAt = datetime("expire_at")
    val maxDownloads = integer("max_downloads").nullable()
    val currentDownloads = integer("current_downloads").default(0)
}