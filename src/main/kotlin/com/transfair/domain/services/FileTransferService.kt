package com.transfair.domain.services

import com.transfair.config.ConfigurationProvider
import com.transfair.domain.exceptions.TechnicalException
import com.transfair.domain.models.FileMetadata
import com.transfair.domain.ports.FileRepository
import com.transfair.domain.ports.StorageService
import com.transfair.routing.dto.input.UploadFileFormDto
import com.transfair.utils.now
import com.transfair.utils.plusSeconds
import kotlinx.datetime.LocalDateTime
import java.util.*

class FileTransferService(
    private val fileRepository: FileRepository,
    private val storageService: StorageService
) {

    private val storageConfig = ConfigurationProvider.storageConfiguration

    suspend fun createMetadata(form: UploadFileFormDto): FileMetadata {
        try {
            val fileId = UUID.randomUUID()

            return fileRepository.saveFile(
                FileMetadata(
                    uuid = fileId,
                    fileName = form.fileName,
                    fileType = form.fileType,
                    fileSize = form.fileSize,
                    checkSum = form.checkSum,
                    password = form.password,
                    title = form.title,
                    description = form.description,
                    maxDownloads = form.maxDownloads ?: 10,
                    createdAt = LocalDateTime.now(),
                    expireAt = form.expiration ?: LocalDateTime.now().plusSeconds(storageConfig.expiration),
                )
            )
        } catch (e: Exception) {
            throw TechnicalException("Unexpected error occurred while create metadata file: ${e.message}")
        }
    }


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