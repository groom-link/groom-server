package com.groom.auth.interfaces.handler

import com.groom.auth.component.JwtGenerator
import com.groom.auth.domain.authentication.AuthCommand
import com.groom.auth.domain.authentication.AuthenticationService
import com.groom.auth.domain.authentication.OAuth2ProviderName
import com.groom.auth.interfaces.api.AuthResponse
import com.groom.common.ResponseSender
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.time.Instant

@Component
internal class OAuth2Handler(
    private val jwtGenerator: JwtGenerator,
    private val responseSender: ResponseSender,
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService,
    private val authenticationService: AuthenticationService
) {
    private val logger = LoggerFactory.getLogger(OAuth2Handler::class.java)
    val oAuth2successLoginHandler =
        { _: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ->
            logger.info(authentication.toString())
            val oauth2User = authentication as OAuth2AuthenticationToken
            val registrationId = oauth2User.authorizedClientRegistrationId
            val client = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
                registrationId, oauth2User.name
            )
            val providerName = OAuth2ProviderName.fromName(
                registrationId
            )
            val command = AuthCommand.OAuth2LoginWithAccessToken(
                providerName, client.accessToken.tokenValue, client.accessToken.scopes
            )
            val resourceAuthentication = authenticationService.loginWithOAuth2Token(
                command
            )
            val accessToken = jwtGenerator.generate(resourceAuthentication.claims, Instant.now())
            responseSender.send(response, AuthResponse.Login(accessToken))
        }
}
