package com.groom.auth.domain.authentication


interface AuthenticationRepository{
    fun findBy(providerName: OAuth2ProviderName, providerUserId: String): Authentication?
    fun createWithOAuth2(create: CreateOAuth2Authentication): Authentication
}