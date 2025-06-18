package com.groom.auth.interfaces.api

import org.springframework.security.oauth2.core.OAuth2AccessToken

internal class AuthResponse private constructor() {
    data class Login(val accessToken: OAuth2AccessToken)
}
