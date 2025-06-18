package com.groom.auth.application

import com.groom.auth.domain.oauth2.OAuth2ProviderName
import com.groom.auth.domain.oauth2.OAuth2UserInformationCommand

class AuthCriteria private constructor() {
    data class OAuth2Login(val accessToken: String,
                           val providerName: OAuth2ProviderName,
                           val providerUserId: String,
                           val attributes: Map<String, Any>) {
        fun toCreateOAuth2UserInfoCommand(): OAuth2UserInformationCommand.Create {
            return OAuth2UserInformationCommand.Create(
                providerName = providerName,
                attributes = attributes,
            )
        }
    }
}