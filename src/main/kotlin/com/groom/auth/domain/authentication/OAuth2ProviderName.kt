package com.groom.auth.domain.authentication

enum class OAuth2ProviderName {
    KAKAO,
    APPLE,
    ;

    companion object {
        fun fromName(name: String): OAuth2ProviderName = OAuth2ProviderName.valueOf(name.uppercase())
    }
}
