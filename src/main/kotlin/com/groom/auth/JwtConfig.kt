package com.groom.auth

import com.groom.common.util.JwtUtil
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.UUID

@Configuration
internal class JwtConfig {

    @Bean
    fun jwtDecoder(rsaKey: RSAKey): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
            .build()
    }

    @Bean
    fun jwtEncoder(jwkSource: JWKSource<SecurityContext?>): JwtEncoder {
        return NimbusJwtEncoder(jwkSource)
    }

    @Bean
    fun jwkSource(rsaKey: RSAKey?): JWKSource<SecurityContext> {
        return ImmutableJWKSet(JWKSet(rsaKey))
    }

    @Bean
    fun rsaKey(keyPair: KeyPair): RSAKey {
        return RSAKey.Builder(keyPair.public as RSAPublicKey)
            .privateKey(keyPair.private as RSAPrivateKey)
            .keyID(UUID.randomUUID()
                .toString())
            .build()
    }

    @Bean
    fun keyPair(): KeyPair {
        return readKeyPairFromFile()
    }

    private fun readKeyPairFromFile(): KeyPair {
        val factory: KeyFactory = KeyFactory.getInstance("RSA")
        val privateKey: PrivateKey = factory.generatePrivate(
            PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(JwtUtil.JWT_PRIVATE_KEY_FILE_NAME)))
        )
        val publicKey: PublicKey = factory.generatePublic(
            X509EncodedKeySpec(Files.readAllBytes(Paths.get(JwtUtil.JWT_PUBLIC_KEY_FILE_NAME)))
        )
        return KeyPair(publicKey, privateKey)
    }
}