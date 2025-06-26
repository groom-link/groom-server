package com.groom.auth.infrastructure.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import com.groom.auth.domain.authentication.OAuth2UserInfoCommand
import org.springframework.stereotype.Component

@Component
internal class OAuth2UserInfoFactory(
    private val objectMapper: ObjectMapper,
) {
    fun create(
        authenticationId: Long,
        data: OAuth2UserInfoCommand.Create,
    ): OAuth2UserInfoEntity =
        with(data) {
            when (providerName) {
                com.groom.auth.domain.authentication.OAuth2ProviderName.KAKAO -> {
                    val userInfo: KakaoUserInfo =
                        objectMapper.convertValue(attributes, KakaoUserInfo::class.java)
                    return OAuth2UserInfoEntity.fromKakao(authenticationId, userInfo)
                }
                else -> throw RuntimeException("지원하지 않는 간편로그인입니다.")
            }
        }
}
