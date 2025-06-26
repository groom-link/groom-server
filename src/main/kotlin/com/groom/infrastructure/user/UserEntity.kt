package com.groom.infrastructure.user

import com.groom.domain.Timestamp
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "users")
internal class UserEntity private constructor(
    @Id
    val authenticationId: Long,
//    val email: String, TODO: 사업자 등록후 가능
    val nickname: String,
    val profileImageUrl: String,
) {
    val timeStamp = Timestamp()
}
