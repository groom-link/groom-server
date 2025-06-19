package com.groom.domain.user

import com.groom.domain.TimeStamp

data class User(
    val id: Long,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    val profileImageUrl: String,
    val timeStamp: TimeStamp
)