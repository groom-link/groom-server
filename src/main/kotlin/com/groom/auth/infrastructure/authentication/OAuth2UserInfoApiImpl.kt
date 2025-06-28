package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.AuthCommand
import com.groom.auth.domain.authentication.OAuth2UserInfoApi
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.stereotype.Repository

@Repository
class OAuth2UserInfoApiImpl(
    private val clientRegistrationRepository: ClientRegistrationRepository,
) : OAuth2UserInfoApi {
    private val oAuth2UserService: DefaultOAuth2UserService = DefaultOAuth2UserService()

    override fun find(command: AuthCommand.OAuth2LoginWithAccessToken): Map<String, Any> {
        val (providerName, accessToken, scope) = command
        val clientRegistration = clientRegistrationRepository.findByRegistrationId(providerName.name)
        val token = OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, null, null, scope)
        val request = OAuth2UserRequest(clientRegistration, token)
        val oAuth2User = oAuth2UserService.loadUser(request)
        return oAuth2User.attributes
    }
}