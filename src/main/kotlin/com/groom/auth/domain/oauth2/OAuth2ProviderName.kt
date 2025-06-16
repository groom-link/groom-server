package com.groom.auth.domain.oauth2

enum class OAuth2ProviderName {
    KAKAO, APPLE;

    companion object {
        fun fromString(s: String): OAuth2ProviderName {
            return OAuth2ProviderName.valueOf(s.uppercase())
        }
    }
}