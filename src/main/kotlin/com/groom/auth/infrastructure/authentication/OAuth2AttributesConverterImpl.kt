package com.groom.auth.infrastructure.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import com.groom.auth.domain.authentication.*
import org.springframework.stereotype.Component

@Component
class OAuth2AttributesConverterImpl(
    private val objectMapper: ObjectMapper,
): OAuth2AttributesConverter {
    override fun convert(
        providerName: OAuth2ProviderName,
        attributes: Map<String, Any>
    ): CreateOAuth2Authentication {
        when (providerName) {
            OAuth2ProviderName.KAKAO -> {
                val userInfo: KakaoUserInfo =
                    objectMapper.convertValue(attributes, KakaoUserInfo::class.java)
                return userInfo.toCreate()
            }
        }
    }
}
