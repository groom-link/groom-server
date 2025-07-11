package com.groom.adapter.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.groom.application.auth.provided.AuthCommand
import com.groom.application.auth.required.OAuth2AttributesConverter
import com.groom.domain.authentication.OAuth2ProviderName
import org.springframework.stereotype.Component

@Component
class OAuth2AttributesConverterImpl(
    private val objectMapper: ObjectMapper,
): OAuth2AttributesConverter {
    override fun convert(
        providerName: OAuth2ProviderName,
        attributes: Map<String, Any>
    ): AuthCommand.CreateOAuth2Authentication {
        when (providerName) {
            OAuth2ProviderName.KAKAO -> {
                val userInfo: KakaoUserInfo =
                    objectMapper.convertValue(attributes, KakaoUserInfo::class.java)
                return userInfo.toCreate()
            }
        }
    }
}
