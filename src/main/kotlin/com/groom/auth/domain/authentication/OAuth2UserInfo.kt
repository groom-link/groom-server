package com.groom.auth.domain.authentication

data class OAuth2UserInfo(
    val id: Long,
    val authenticationId: Long,
    val providerName: OAuth2ProviderName,
    val providerUserId: String,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    val profileImageUrl: String,
)
