package com.transfair.infrastructure.database.entities

import com.transfair.domain.models.FileMetadata
import com.transfair.infrastructure.database.tables.FilesTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

class FileEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<FileEntity>(table = FilesTable)

    var fileName by FilesTable.fileName
    var fileSize by FilesTable.fileSize
    var fileType by FilesTable.fileType
    var checkSum by FilesTable.checkSum
    var password by FilesTable.password
    var title by FilesTable.title
    var description by FilesTable.description
    var createdAt by FilesTable.createdAt
    var expireAt by FilesTable.expireAt
    var maxDownloads by FilesTable.maxDownloads
    var currentDownloads by FilesTable.currentDownloads


    fun toDomain(): FileMetadata {
        return FileMetadata(
            uuid = id.value,
            fileName = fileName,
            fileType = fileType,
            fileSize = fileSize,
            checkSum = checkSum,
            password = password,
            title = title,
            description = description,
            createdAt = createdAt,
            expireAt = expireAt,
            maxDownloads = maxDownloads,
            currentDownloads = currentDownloads
        )
    }
}