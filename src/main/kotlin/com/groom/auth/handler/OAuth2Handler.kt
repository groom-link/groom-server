package com.groom.auth.handler

import com.groom.auth.CustomOAuth2User
import com.groom.auth.component.JwtService
import com.groom.auth.interfaces.JwtResponse
import com.groom.common.ResponseSender
import com.groom.auth.domain.authentication.AuthenticationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Instant

@Component
internal class OAuth2Handler(
    private val authenticationService: AuthenticationService,
    private val jwtService: JwtService,
    private val responseSender: ResponseSender
) {
    private val logger = LoggerFactory.getLogger(OAuth2Handler::class.java)
    val oAuth2successLoginHandler =
        { _: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ->
            logger.info(authentication.toString())
            val oauth2User = authentication.principal as CustomOAuth2User
            val accessToken =
                jwtService.generateToken(oauth2User.authentication.claims, Instant.now())
            responseSender.send(response, JwtResponse(accessToken))
        }
}