package com.groom.application.auth.required

import com.groom.application.auth.provided.AuthCommand
import com.groom.domain.authentication.OAuth2ProviderName

interface OAuth2AttributesConverter {
    fun convert(providerName: OAuth2ProviderName, attributes: Map<String, Any>): AuthCommand.CreateOAuth2Authentication
}