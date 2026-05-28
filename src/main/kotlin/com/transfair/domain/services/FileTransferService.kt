package com.transfair.domain.services

import com.transfair.config.ConfigurationProvider
import com.transfair.domain.models.FileMetadata
import com.transfair.domain.ports.FileRepository
import com.transfair.domain.ports.StorageService
import com.transfair.utils.now
import com.transfair.utils.plusSeconds
import kotlinx.datetime.LocalDateTime
import java.io.InputStream
import java.util.*

class FileTransferService(
    private val fileRepository: FileRepository,
    private val storageService: StorageService
) {

    private val storageConfig = ConfigurationProvider.storageConfiguration

    suspend fun uploadFile(fileName: String, size: Long, content: InputStream): FileMetadata {
        try {
            val fileId = UUID.randomUUID()

            storageService.saveFile(fileId, content);

            return fileRepository.saveFile(
                FileMetadata(
                    fileId,
                    fileName,
                    size,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusSeconds(storageConfig.expiration),
                    1
                )
            )
        } catch (e: Exception) {
            throw e
        }


    }
}