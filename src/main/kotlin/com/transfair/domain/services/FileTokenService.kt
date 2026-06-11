package com.transfair.domain.services

import com.transfair.config.ConfigurationProvider
import com.transfair.utils.now
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.security.Key
import java.time.ZoneOffset
import java.util.*
import javax.crypto.spec.SecretKeySpec

class FileTokenService {

    companion object {

        private val config = ConfigurationProvider.tokenConfiguration

        fun generateUploadToken(uploadId: UUID, expiration: LocalDateTime): String {
            return Jwts.builder()
                .issuer("Transfair")
                .subject(uploadId.toString())
                .signWith(getKey())
                .expiration(Date.from(expiration.toJavaLocalDateTime().toInstant(ZoneOffset.UTC)))
                .compact()
        }

        fun validateJwt(token: String, uploadId: UUID): Boolean {
            val claims = extract(token).payload

            val expiration: Boolean = claims.expiration.after(Date.from(LocalDateTime.now().toJavaLocalDateTime().toInstant(ZoneOffset.UTC)))
            val id: Boolean = claims.subject == uploadId.toString()
            return expiration && id
        }

        private fun extract(token: String): Jws<Claims> {
            return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
        }

        private fun getKey(): Key {
            return Keys.hmacShaKeyFor(config.secret.toByteArray())
        }

        private fun getSecretKey(): SecretKeySpec {
            return SecretKeySpec(config.secret.toByteArray(), getKey().algorithm)
        }


    }
}