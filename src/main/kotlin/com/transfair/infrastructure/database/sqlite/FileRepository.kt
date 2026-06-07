package com.transfair.infrastructure.database.sqlite

import com.transfair.domain.models.FileMetadata
import com.transfair.domain.ports.FileRepository
import com.transfair.infrastructure.database.entities.FileEntity
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

class FileRepository : FileRepository {
    override suspend fun saveFile(fileMetadata: FileMetadata): FileMetadata = transaction {
        FileEntity.new(fileMetadata.uuid) {
            fileName = fileMetadata.fileName
            size = fileMetadata.size
            createdAt = fileMetadata.createdAt
            expireAt = fileMetadata.expireAt
            maxDownloads = fileMetadata.maxDownloads
        }
        return@transaction fileMetadata
    }


    override suspend fun findById(uuid: UUID): FileMetadata? = transaction {
        FileEntity.findById(uuid)?.toDomain()
    }

    override suspend fun deleteFile(uuid: UUID): Boolean = transaction {
        val entity = FileEntity.findById(uuid) ?: return@transaction false
        entity.delete()
        return@transaction true
    }

    override suspend fun incrementDownloads(uuid: UUID): Boolean = transaction {
        val entity = FileEntity.findById(uuid) ?: return@transaction false
        entity.currentDownloads += 1
        return@transaction true
    }
}