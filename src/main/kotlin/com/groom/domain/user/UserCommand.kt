package com.groom.domain.user

class UserCommand private constructor() {
    data class Create(
        val authenticationId: Long,
//        val email: String, TODO: 사업자 등록후 가능
        val nickname: String ,
        val profileImageUrl: String,
    )
}