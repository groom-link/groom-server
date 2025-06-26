package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.OAuth2ProviderName
import com.groom.auth.domain.authentication.OAuth2UserInfo
import com.groom.infrastructure.common.EntityTimeStamp
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "oauth2_informations")
internal data class OAuth2UserInfoEntity(
    val authenticationId: Long,
    val providerUserId: String,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    @Enumerated(EnumType.STRING)
    val providerName: OAuth2ProviderName,
    val profileImageUrl: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    val timestamp: EntityTimeStamp = EntityTimeStamp()

    fun toDomain(): OAuth2UserInfo =
        OAuth2UserInfo(
            id = id,
            authenticationId = authenticationId,
            providerName = providerName,
            providerUserId = providerUserId,
//            email = email, TODO: 사업자 등록후 가능
            nickname = nickname,
            profileImageUrl = profileImageUrl,
        )

    companion object {
        fun fromKakao(
            authenticationId: Long,
            userInfo: KakaoUserInfo,
        ): OAuth2UserInfoEntity =
            OAuth2UserInfoEntity(
                authenticationId = authenticationId,
                providerUserId = userInfo.id.toString(),
//                email = information.email,
                providerName = OAuth2ProviderName.KAKAO,
                nickname = userInfo.kakaoAccount.profile.nickname,
                profileImageUrl = userInfo.kakaoAccount.profile.profileImageUrl,
            )
    }
}
