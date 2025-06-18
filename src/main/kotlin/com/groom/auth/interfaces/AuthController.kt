package com.groom.auth.interfaces

import com.groom.auth.CustomOAuth2User
import com.groom.auth.component.JwtGenerator
import com.groom.common.Response
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("api/v1/auth")
class AuthController(private val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
                     private val clientRegistrationRepository: ClientRegistrationRepository,
                     private val jwtGenerator: JwtGenerator) {
    @PostMapping("/login/oauth2/token/{providerName}")
    fun oAuth2LoginWithToken(@PathVariable providerName: String,
                             @RequestBody body: AuthRequest.OAuth2LoginWithAccessToken): Response<AuthResponse.Login> {
        val clientRegistration = clientRegistrationRepository.findByRegistrationId(providerName)
        val request = OAuth2UserRequest(clientRegistration, body.token)
        val oAuth2User = oAuth2UserService.loadUser(request) as CustomOAuth2User
        return Response(AuthResponse.Login(jwtGenerator.generate(oAuth2User.authentication.claims,
            Instant.now())))
    }
}