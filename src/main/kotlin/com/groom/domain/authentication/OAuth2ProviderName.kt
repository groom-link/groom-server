package com.groom.domain.authentication

enum class OAuth2ProviderName {
    KAKAO,
    ;

    companion object {
        fun fromName(name: String): OAuth2ProviderName = OAuth2ProviderName.valueOf(name.uppercase())
    }
}
