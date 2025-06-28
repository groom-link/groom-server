package com.groom.auth.domain.authentication

import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "oauth2_authentications")
data class OAuth2Authentication private constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authentication_id")
    val authentication: Authentication,
    val providerUserId: String,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    @Enumerated(EnumType.STRING)
    val providerName: OAuth2ProviderName,
    val profileImageUrl: String,
) {
    @EmbeddedId
    val pk = CommonId()
    val timestamp = Timestamp()

    companion object {
        fun of(
            create: CreateOAuth2Authentication,
            authentication: Authentication = Authentication(),
        ): OAuth2Authentication =
            OAuth2Authentication(
                authentication = authentication,
                providerUserId = create.providerUserId,
                nickname = create.nickname,
                providerName = create.providerName,
                profileImageUrl = create.profileImageUrl,
            )
    }
}
