package com.groom.auth.handler

import com.groom.auth.CustomOAuth2User
import com.groom.auth.component.JwtGenerator
import com.groom.auth.interfaces.AuthResponse
import com.groom.common.ResponseSender
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Instant

@Component
internal class OAuth2Handler(
    private val jwtGenerator: JwtGenerator,
    private val responseSender: ResponseSender
) {
    private val logger = LoggerFactory.getLogger(OAuth2Handler::class.java)
    val oAuth2successLoginHandler =
        { _: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ->
            logger.info(authentication.toString())
            val oauth2User = authentication.principal as CustomOAuth2User
            val accessToken =
                jwtGenerator.generate(oauth2User.authentication.claims, Instant.now())
            responseSender.send(response, AuthResponse.Login(accessToken))
        }
}