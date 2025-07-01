package com.groom.application.auth

import com.groom.application.auth.provided.AuthCommand
import com.groom.application.auth.provided.Login
import com.groom.application.auth.required.AuthenticationRepository
import com.groom.application.auth.required.OAuth2AttributesConverter
import com.groom.application.auth.required.OAuth2UserInfoFinder
import com.groom.application.profile.provided.CreateProfile
import com.groom.application.profile.provided.ProfileCommand
import com.groom.domain.authentication.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AuthenticationService internal constructor(
    private val oAuth2UserInfoFinder: OAuth2UserInfoFinder,
    private val attributesConverter: OAuth2AttributesConverter,
    private val authenticationRepository: AuthenticationRepository,
    private val createProfile: CreateProfile
) : Login {
    @Transactional
    override fun loginWithOAuth2Token(command: AuthCommand.OAuth2LoginWithAccessToken): Authentication {
        val attributesMap = oAuth2UserInfoFinder.find(command)
        val createDto = attributesConverter.convert(command.providerName, attributesMap)
        val authentication =
            authenticationRepository.findBy(createDto.providerName, createDto.providerUserId) ?: registerWithOAuth2(
                createDto
            )
        val createProfileCommand = ProfileCommand.Create(
            authenticationPk = authentication.pk,
            nickname = createDto.nickname,
            profileImageUrl = createDto.profileImageUrl
        )
        this.createProfile.create(createProfileCommand)
        return authentication
    }

    private fun registerWithOAuth2(createDto: AuthCommand.CreateOAuth2Authentication): Authentication {
        val authentication = authenticationRepository.save(Authentication())
        authentication.registerOAuth2(createDto)
        return authentication
    }
}