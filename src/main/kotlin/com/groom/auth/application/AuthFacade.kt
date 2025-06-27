package com.groom.auth.application

import com.groom.auth.domain.authentication.AuthenticationService
import com.groom.auth.domain.authentication.OAuth2UserInfoCommand
import com.groom.auth.domain.authentication.OAuth2UserInfoService
import com.groom.domain.user.UserCommand
import com.groom.domain.user.UserService
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val userService: UserService,
    private val oAuth2UserInfoService: OAuth2UserInfoService,
    private val authenticationService: AuthenticationService,
) {
    fun loginOrSignUpForOAuth2(criteria: AuthCriteria.OAuth2Login): Authentication =
        oAuth2UserInfoService
            .read(
                providerName = criteria.providerName,
                providerUserId = criteria.providerUserId,
            )?.let { authenticationService.read(it.authenticationId) }
            ?: registerNewOAuth2User(criteria.toCreateOAuth2UserInfoCommand())

    private fun registerNewOAuth2User(command: OAuth2UserInfoCommand.Create): Authentication {
        val authentication = authenticationService.create()
        val oAuth2UserInfo = oAuth2UserInfoService.create(authentication.id, command)
        userService.create(
            UserCommand.Create(
                authenticationId = authentication.id,
                nickname = oAuth2UserInfo.nickname,
                profileImageUrl = oAuth2UserInfo.profileImageUrl,
            ),
        )
        return authentication
    }
}
