package com.groom.auth.domain.authentication

import org.springframework.security.oauth2.core.OAuth2AccessToken


class AuthCommand {
    data class OAuth2LoginWithAccessToken(
        val providerName: OAuth2ProviderName, val accessToken: String, val scopes: Set<String>
    )
}