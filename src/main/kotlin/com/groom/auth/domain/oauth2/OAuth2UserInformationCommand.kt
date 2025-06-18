package com.groom.auth.domain.oauth2

class OAuth2UserInformationCommand private constructor() {
    data class Create(
        val providerName: OAuth2ProviderName,
        val attributes: Map<String, Any>)
}