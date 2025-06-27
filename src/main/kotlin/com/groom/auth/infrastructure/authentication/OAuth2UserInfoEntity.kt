package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.OAuth2ProviderName
import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity(name = "oauth2_informations")
data class OAuth2UserInfoEntity(
    val authenticationId: Long,
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
}
