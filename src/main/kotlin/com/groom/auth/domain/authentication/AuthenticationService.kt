package com.groom.auth.domain.authentication

import org.springframework.stereotype.Component

@Component
class AuthenticationService internal constructor(
    private val oAuth2UserInfoApi: OAuth2UserInfoApi,
    private val attributesConverter: OAuth2AttributesConverter,
    private val authenticationRepository: AuthenticationRepository
) {
    fun loginWithOAuth2Token(command: AuthCommand.OAuth2LoginWithAccessToken): Authentication {
        val attributesMap = oAuth2UserInfoApi.find(command)
        val create = attributesConverter.convert(command.providerName, attributesMap)
        return authenticationRepository.findBy(create.providerName, create.providerUserId)
            ?: authenticationRepository.createWithOAuth2(create)
    }
}