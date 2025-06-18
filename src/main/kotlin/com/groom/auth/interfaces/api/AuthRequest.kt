package com.groom.auth.interfaces.api

import org.springframework.security.oauth2.core.OAuth2AccessToken
import java.time.Instant


internal class AuthRequest private constructor() {
    data class OAuth2LoginWithAccessToken(
        val accessToken: String,
        val issuedAt: Instant,
        val expiresAt: Instant,
        val scopes: Set<String>) {
        val token: OAuth2AccessToken get() =
            OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                accessToken,
                issuedAt,
                expiresAt,
                scopes)

    }
}