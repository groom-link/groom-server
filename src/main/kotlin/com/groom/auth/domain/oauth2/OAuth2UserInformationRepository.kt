package com.groom.auth.domain.oauth2

internal interface OAuth2UserInformationRepository{
    fun findBy(providerName: OAuth2ProviderName,
               providerUserId: String): OAuth2UserInformation?

    fun create(authenticationId: Long, data: OAuth2UserInformationCommand.Create): OAuth2UserInformation
}