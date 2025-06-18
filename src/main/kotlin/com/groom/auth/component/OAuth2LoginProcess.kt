package com.groom.auth.component

import com.groom.auth.application.AuthCriteria
import com.groom.auth.application.AuthFacade
import com.groom.auth.CustomOAuth2User
import com.groom.auth.domain.oauth2.OAuth2ProviderName
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component


@Component
class OAuth2LoginProcess(
    private val authFacade: AuthFacade,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val oAuth2UserService: DefaultOAuth2UserService = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = oAuth2UserService.loadUser(userRequest)
        val attributes = oAuth2User.attributes
        val providerName = OAuth2ProviderName.fromName(userRequest.clientRegistration.clientName)
        val nameAttributeName = userRequest.clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName
        val authentication =
            authFacade.loginOrSignUpForOAuth2(AuthCriteria.OAuth2Login(accessToken = userRequest.accessToken.tokenValue,
                providerName = providerName,
                providerUserId = oAuth2User.name,
                attributes = attributes))
        return CustomOAuth2User(authentication, attributes, nameAttributeName)
    }
}