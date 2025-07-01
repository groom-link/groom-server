package com.groom.application.auth.provided

import com.groom.domain.authentication.Authentication
import com.groom.domain.authentication.OAuth2ProviderName

interface Login {
    fun loginWithOAuth2Token(command: AuthCommand.OAuth2LoginWithAccessToken): Authentication
}

class AuthCommand {
    data class OAuth2LoginWithAccessToken(
        val providerName: OAuth2ProviderName, val accessToken: String, val scopes: Set<String>
    )

    data class CreateOAuth2Authentication(
        val providerName: OAuth2ProviderName,
        val providerUserId: String,
//    val email: String, TODO: 사업자 등록 후 가능
        val nickname: String,
        val profileImageUrl: String,
    )
}