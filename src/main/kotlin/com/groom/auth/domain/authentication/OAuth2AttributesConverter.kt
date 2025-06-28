package com.groom.auth.domain.authentication

interface OAuth2AttributesConverter {
    fun convert(providerName: OAuth2ProviderName, attributes: Map<String, Any>): CreateOAuth2Authentication
}