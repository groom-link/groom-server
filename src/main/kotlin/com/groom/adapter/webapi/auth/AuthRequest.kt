package com.groom.adapter.webapi.auth

import java.time.Instant


internal class AuthRequest private constructor() {
    data class OAuth2LoginWithAccessToken(
        val accessToken: String,
        val issuedAt: Instant,
        val expiresAt: Instant,
        val scopes: Set<String>)
}