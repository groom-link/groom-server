package com.groom.auth.component

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant

@Component
class JwtGenerator(
    private val jwtEncoder: JwtEncoder,
    @Value("\${spring.security.jwt.access.expires-in-seconds:3600}")
    private val accessTokenExpiresInSeconds: Long,
) {

    fun generate(claims: Map<String, String> = mapOf(), issuedAt: Instant,
                 expiresInSeconds: Long = accessTokenExpiresInSeconds): OAuth2AccessToken {
        val expiredAt = issuedAt.plus(Duration.ofSeconds(expiresInSeconds))
        val claimSet: JwtClaimsSet = JwtClaimsSet.builder()
            .claims { claim -> claim.putAll(claims) }
            .expiresAt(expiredAt)
            .issuedAt(issuedAt)
            .build()
        val encode: Jwt = jwtEncoder.encode(JwtEncoderParameters.from(claimSet))
        return OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER, encode.tokenValue, issuedAt,
            expiredAt
        )
    }
}