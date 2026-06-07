package com.transfair.domain.services

import com.transfair.config.ConfigurationProvider
import com.transfair.domain.exceptions.TechnicalException
import com.transfair.domain.models.FileMetadata
import com.transfair.domain.ports.FileRepository
import com.transfair.domain.ports.StorageService
import com.transfair.routing.dto.input.UploadFileFormDto
import com.transfair.utils.now
import com.transfair.utils.plusSeconds
import io.ktor.utils.io.*
import kotlinx.datetime.LocalDateTime
import java.io.InputStream
import java.util.*

class FileTransferService(
    private val fileRepository: FileRepository,
    private val storageService: StorageService
) {

    private val storageConfig = ConfigurationProvider.storageConfiguration

    suspend fun uploadFile(form: UploadFileFormDto, content: ByteReadChannel): FileMetadata {
        try {
            val fileId = UUID.randomUUID()
            storageService.saveFile(fileId, content)

            return fileRepository.saveFile(
                FileMetadata(
                    fileId,
                    form.fileName,
                    form.size,
                    LocalDateTime.now(),
                    form.expiration ?: LocalDateTime.now().plusSeconds(storageConfig.expiration),
                    form.maxDownloads ?: 10,
                )
            )
        } catch (e: Exception) {
            throw TechnicalException("Unexpected error occurred while uploading file: ${e.message}")
        }
    }

    suspend fun getFile(fileId: UUID): InputStream = storageService.getFile(fileId)

    suspend fun getFileMetadata(fileId: UUID): FileMetadata {
        val metadata =
            fileRepository.findById(fileId) ?: throw TechnicalException("Metadata of the file $fileId are not found")

        if (metadata.currentDownloads == metadata.maxDownloads) {
            this.deleteFile(fileId)
            throw TechnicalException("File $fileId has reached its maximum downloads")
        }
        fileRepository.incrementDownloads(fileId)
        return metadata
    }

    suspend fun deleteFile(fileId: UUID): Boolean {
        val isDeleted = storageService.deleteFile(fileId)
        if (isDeleted) {
            return fileRepository.deleteFile(fileId)
        }
        return false
    }
}