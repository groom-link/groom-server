package com.groom.auth.infrastructure.oauth2

import com.fasterxml.jackson.annotation.JsonProperty


internal class KakaoUserInfo(val id: Long, @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount) {
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
            @field:JsonProperty("profile_image_url") @param:JsonProperty("profile_image_url") val profileImageUrl: String
        )
    }
}