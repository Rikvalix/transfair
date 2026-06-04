package com.transfair.domain.ports

import com.transfair.domain.models.FileMetadata
import java.util.*

interface FileRepository {

    /**
     * @param fileMetadata metadata of the file to be saved
     */
    suspend fun saveFile(fileMetadata: FileMetadata): FileMetadata

    /**
     * @param uuid unique identifier of the file
     */
    suspend fun findById(uuid: UUID): FileMetadata?

    /**
     * @param uuid unique identifier of the file
     */
    suspend fun deleteFile(uuid: UUID): Boolean

    /**
     * @param uuid unique identifier of the file
     */
    suspend fun incrementDownloads(uuid: UUID): Boolean
}