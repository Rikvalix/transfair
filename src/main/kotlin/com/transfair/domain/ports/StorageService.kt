package com.transfair.domain.ports

import java.io.File
import java.io.InputStream
import java.util.*

interface StorageService {

    suspend fun saveFile(fileId: UUID, content: InputStream): File

    suspend fun getFile(fileId: UUID): InputStream?

    suspend fun deleteFile(fileId: UUID): Boolean
}