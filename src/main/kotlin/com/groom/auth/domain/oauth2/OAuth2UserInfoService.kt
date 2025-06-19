package com.groom.auth.domain.oauth2

import org.springframework.stereotype.Service

@Service
class OAuth2UserInfoService internal constructor(private val oAuth2UserInfoRepository: OAuth2UserInfoRepository) {
    fun read(providerName: OAuth2ProviderName, providerUserId: String): OAuth2UserInfo? =
        oAuth2UserInfoRepository.findBy(providerName, providerUserId)

    fun create(authenticationId: Long,
               command: OAuth2UserInfoCommand.Create): OAuth2UserInfo {
        return oAuth2UserInfoRepository.create(authenticationId, command)
    }
}