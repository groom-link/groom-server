package com.groom.auth.infrastructure.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import com.groom.auth.domain.oauth2.CreateOAuth2UserInformation
import com.groom.auth.domain.oauth2.OAuth2ProviderName
import org.springframework.stereotype.Component

@Component
internal class OAuth2VOMapper(private val objectMapper: ObjectMapper) {
    fun convert(providerName: OAuth2ProviderName,
                attributes: Map<String, Any>): CreateOAuth2UserInformation {
        when (providerName) {
            OAuth2ProviderName.KAKAO -> {
                val userInfo: KakaoUserInfo =
                    objectMapper.convertValue(attributes, KakaoUserInfo::class.java)
                return toOAuth2UserInformation(userInfo)
            }

            else -> throw RuntimeException("지원하지 않는 간편로그인입니다.")
        }
    }

    private fun toOAuth2UserInformation(kakaoUserInfo: KakaoUserInfo): CreateOAuth2UserInformation {
        return CreateOAuth2UserInformation(
            providerName = OAuth2ProviderName.KAKAO,
            providerUserId = kakaoUserInfo.id.toString(),
//            email = kakaoUserInfo.kakaoAccount.email, TODO: 사업자 등록 후 가능
            nickname = kakaoUserInfo.kakaoAccount.profile.nickname,
            profileImageUrl = kakaoUserInfo.kakaoAccount.profile.profileImageUrl,
        )
    }
}