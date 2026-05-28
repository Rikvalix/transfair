package com.transfair.infrastructure.storage

import com.transfair.domain.ports.StorageService
import io.ktor.http.content.PartData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.naming.Context
import kotlin.io.path.Path
import kotlin.io.path.exists

class LocalStorageService : StorageService {

    override suspend fun saveFile(fileId: UUID, content: InputStream): Boolean {
        withContext(Dispatchers.IO) {
            Files.createDirectories(Paths.get("files"))
            Files.createFile(Paths.get("files/$fileId"))
        }
        val file = File("files/$fileId")
        file.writeBytes(content.readBytes())
        return true
    }

    override suspend fun getFile(fileId: UUID): InputStream? {
        val pathExist = Path("files/${fileId}").exists()
        return if (pathExist)
            File("files/${fileId}").inputStream()
        else
            null
    }

    override suspend fun deleteFile(fileId: UUID): Boolean {
        return File("files/${fileId}").delete()
    }
}