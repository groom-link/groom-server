package com.groom.auth.domain.oauth2

interface OAuth2UserInfoRepository{
    fun findBy(providerName: OAuth2ProviderName,
               providerUserId: String): OAuth2UserInfo?

    fun create(authenticationId: Long, data: OAuth2UserInfoCommand.Create): OAuth2UserInfo
}