package com.groom.auth.domain.oauth2

import org.springframework.stereotype.Service

@Service
class OAuth2UserInformationService internal constructor(private val oAuth2UserInformationRepository: OAuth2UserInformationRepository) {
    fun read(providerName: OAuth2ProviderName, providerUserId: String): OAuth2UserInformation? =
        oAuth2UserInformationRepository.findBy(providerName, providerUserId)

    fun create(authenticationId: Long,
               command: OAuth2UserInformationCommand.Create): OAuth2UserInformation {
        return oAuth2UserInformationRepository.create(authenticationId, command)
    }
}