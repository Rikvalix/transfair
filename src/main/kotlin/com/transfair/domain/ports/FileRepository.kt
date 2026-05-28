package com.transfair.domain.ports

import com.transfair.domain.models.FileMetadata
import java.util.UUID

interface FileRepository {

    suspend fun saveFile(fileMetadata: FileMetadata) : FileMetadata

    suspend fun findById(uuid: UUID) : FileMetadata?

    suspend fun deleteFile(uuid: UUID) : Boolean

    suspend fun incrementDownloads(uuid: UUID) : Boolean
}