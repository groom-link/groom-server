package com.groom.adapter.webapi.auth

import com.groom.application.auth.provided.AuthCommand
import com.groom.application.auth.provided.Login
import com.groom.domain.authentication.JwtGenerator
import com.groom.domain.authentication.OAuth2ProviderName
import com.groom.shared.Response
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("api/v1/auth")
class AuthController(private val login: Login, private val jwtGenerator: JwtGenerator) {
    @PostMapping("/login/oauth2/token/{providerName}")
    internal fun oAuth2LoginWithToken(
        @PathVariable providerName: String, @RequestBody body: AuthRequest.OAuth2LoginWithAccessToken
    ): Response<AuthResponse.Login> {
        val command = AuthCommand.OAuth2LoginWithAccessToken(
            providerName = OAuth2ProviderName.fromName(providerName),
            accessToken = body.accessToken,
            scopes = body.scopes,
        )
        val authentication = login.loginWithOAuth2Token(command)
        return Response(
            AuthResponse.Login(
                jwtGenerator.generate(
                    authentication.claims, Instant.now()
                )
            )
        )
    }
}