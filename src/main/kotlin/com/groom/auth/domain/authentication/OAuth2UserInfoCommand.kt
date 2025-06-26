package com.groom.auth.domain.authentication

class OAuth2UserInfoCommand private constructor() {
    data class Create(
        val providerName: OAuth2ProviderName,
        val attributes: Map<String, Any>,
    )
}
