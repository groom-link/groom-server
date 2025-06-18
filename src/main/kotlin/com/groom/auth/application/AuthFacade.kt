package com.groom.auth.application

import com.groom.auth.domain.authentication.Authentication
import com.groom.auth.domain.authentication.AuthenticationService
import com.groom.auth.domain.oauth2.OAuth2UserInformationCommand
import com.groom.auth.domain.oauth2.OAuth2UserInformationService
import com.groom.domain.user.UserCommand
import com.groom.domain.user.UserService
import org.springframework.stereotype.Component

@Component
class AuthFacade(private val userService: UserService,
                 private val oAuth2UserInformationService: OAuth2UserInformationService,
                 private val authenticationService: AuthenticationService) {

    fun loginOrSignUpForOAuth2(criteria: AuthCriteria.OAuth2Login): Authentication {
        return oAuth2UserInformationService.read(
            providerName = criteria.providerName,
            providerUserId = criteria.providerUserId)
            ?.let {
                authenticationService.read(it.authenticationId)
            }
            ?: registerNewOAuth2User(criteria.toCreateOAuth2UserInfoCommand())
    }

    private fun registerNewOAuth2User(command: OAuth2UserInformationCommand.Create): Authentication {
        val authentication = authenticationService.create()
        val oAuth2UserInfo = oAuth2UserInformationService.create(authentication.id, command)
        userService.create(UserCommand.Create(
            authenticationId = authentication.id,
            nickname = oAuth2UserInfo.nickname,
            profileImageUrl = oAuth2UserInfo.profileImageUrl)
        )
        return authentication
    }
}