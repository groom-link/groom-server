package com.groom.domain.authentication

import com.groom.application.auth.provided.AuthCommand
import com.groom.domain.shared.CommonEntity
import jakarta.persistence.*

@Entity(name = "oauth2_authentications")
class OAuth2Authentication private constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authentication_id")
    val authentication: Authentication,
    val providerUserId: String,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    @Enumerated(EnumType.STRING) val providerName: OAuth2ProviderName,
    val profileImageUrl: String,
) : CommonEntity() {

    companion object {
        fun forRegister(
            create: AuthCommand.CreateOAuth2Authentication,
            authentication: Authentication = Authentication(),
        ): OAuth2Authentication = OAuth2Authentication(
            authentication = authentication,
            providerUserId = create.providerUserId,
            nickname = create.nickname,
            providerName = create.providerName,
            profileImageUrl = create.profileImageUrl,
        )
    }
}
