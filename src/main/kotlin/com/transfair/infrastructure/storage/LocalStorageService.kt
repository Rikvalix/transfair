package com.transfair.infrastructure.storage

import com.transfair.domain.exceptions.FunctionalException
import com.transfair.domain.ports.StorageService
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.inputStream

class LocalStorageService : StorageService {

    override suspend fun saveFile(fileId: UUID, content: ByteReadChannel): Boolean {
        return withContext(Dispatchers.IO) {
            Files.createDirectories(Paths.get("files"))
            Files.createFile(Paths.get("files/$fileId"))
            val file = File("files/$fileId")
            content.copyAndClose(file.writeChannel())

            true
        }
    }

    override suspend fun getFile(fileId: UUID): InputStream {
        return withContext(Dispatchers.IO) {
            val pathExist = Path("files/${fileId}")
            if (pathExist.exists())
                pathExist.inputStream()
            else
                throw FunctionalException(status = NotFound, message = "File $fileId not found")
        }

    }

    override suspend fun deleteFile(fileId: UUID): Boolean {
        return File("files/${fileId}").delete()
    }
}