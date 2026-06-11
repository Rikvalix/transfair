package com.unit

import com.transfair.config.ConfigurationProvider
import com.transfair.config.TokenConfiguration
import com.transfair.domain.services.FileTokenService
import com.transfair.utils.now
import com.transfair.utils.plusSeconds
import io.jsonwebtoken.Jwts
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeAll
import java.util.*
import kotlin.test.Test
import kotlin.test.assertTrue

class FileTokenServiceTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun init() {
            ConfigurationProvider.initialize(
                TokenConfiguration(
                    secret = Jwts.SIG.HS256.key().build().encoded.decodeToString()
                )
            )
        }
    }

    @Test
    fun `generate jwt token`() {
        // Given
        val id = UUID.randomUUID()
        val expiration = LocalDateTime.now().plusSeconds(3000)

        // Then
        val token: String = FileTokenService.generateUploadToken(id, expiration)
        assertTrue(FileTokenService.validateJwt(token, id))
    }



}