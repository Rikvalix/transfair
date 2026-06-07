package com.transfair.domain.ports

import io.ktor.utils.io.*
import java.io.InputStream
import java.util.*

interface StorageService {

    /**
     * @param fileId unique identifier of the file
     * @param content content of the file
     * @return true if the file was saved successfully, false otherwise
     */
    suspend fun saveFile(fileId: UUID, content: ByteReadChannel): Boolean

    /**
     * @param fileId unique identifier of the file
     * @return the file content if it exists, null otherwise
     */
    suspend fun getFile(fileId: UUID): InputStream

    /**
     * @param fileId unique identifier of the file
     * @return true if the file was deleted successfully, false otherwise
     */
    suspend fun deleteFile(fileId: UUID): Boolean
}