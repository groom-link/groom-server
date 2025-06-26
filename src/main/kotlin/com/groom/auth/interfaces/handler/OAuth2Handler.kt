package com.groom.auth.interfaces.handler

import com.groom.auth.application.AuthCriteria
import com.groom.auth.application.AuthFacade
import com.groom.auth.component.JwtGenerator
import com.groom.auth.domain.authentication.OAuth2ProviderName
import com.groom.auth.interfaces.api.AuthResponse
import com.groom.auth.interfaces.api.CustomOAuth2User
import com.groom.common.ResponseSender
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.time.Instant

@Component
internal class OAuth2Handler(
    private val jwtGenerator: JwtGenerator,
    private val responseSender: ResponseSender,
    private val authFacade: AuthFacade,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val oAuth2UserService: DefaultOAuth2UserService = DefaultOAuth2UserService()

    private val logger = LoggerFactory.getLogger(OAuth2Handler::class.java)
    val oAuth2successLoginHandler =
        { _: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ->
            logger.info(authentication.toString())
            val oauth2User = authentication.principal as CustomOAuth2User
            val accessToken =
                jwtGenerator.generate(oauth2User.authentication.claims, Instant.now())
            responseSender.send(response, AuthResponse.Login(accessToken))
        }

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = oAuth2UserService.loadUser(userRequest)
        val attributes = oAuth2User.attributes
        val providerName = OAuth2ProviderName.fromName(userRequest.clientRegistration.clientName)
        val nameAttributeName =
            userRequest.clientRegistration
                .providerDetails
                .userInfoEndpoint
                .userNameAttributeName
        val authentication =
            authFacade.loginOrSignUpForOAuth2(
                AuthCriteria.OAuth2Login(
                    accessToken = userRequest.accessToken.tokenValue,
                    providerName = providerName,
                    providerUserId = oAuth2User.name,
                    attributes = attributes,
                ),
            )
        return CustomOAuth2User(authentication, attributes, nameAttributeName)
    }
}
