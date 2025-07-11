package com.groom.adapter.security

import com.fasterxml.jackson.annotation.JsonProperty
import com.groom.application.auth.provided.AuthCommand
import com.groom.domain.authentication.OAuth2ProviderName

internal class KakaoUserInfo(
    val id: Long,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount,
) {
    class KakaoAccount(
        val profile: Profile,
//        TODO: 사업자 등록 후 가능
//        val name: String,
//        val email: String,
//        @field:JsonProperty("phone_number") @JsonProperty("phone_number") val phoneNumber: String,
//        val ci: String
    ) {
        @JvmRecord
        data class Profile(
            val nickname: String,
            @field:JsonProperty("thumbnail_image_url") @param:JsonProperty("thumbnail_image_url") val thumbnail_image_url: String,
            @field:JsonProperty("profile_image_url") @param:JsonProperty("profile_image_url") val profileImageUrl: String,
        )
    }

    fun toCreate() = AuthCommand.CreateOAuth2Authentication(
        providerName = OAuth2ProviderName.KAKAO,
        providerUserId = id.toString(),
        nickname = kakaoAccount.profile.nickname,
        profileImageUrl = kakaoAccount.profile.profileImageUrl
    )
}
