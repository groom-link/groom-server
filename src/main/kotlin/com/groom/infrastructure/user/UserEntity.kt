package com.groom.infrastructure.user

import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import org.hibernate.annotations.NaturalId

@Entity(name = "users")
internal class UserEntity private constructor(
    @NaturalId
    val authenticationId: Long,
//    val email: String, TODO: 사업자 등록후 가능
    @NaturalId
    val nickname: String,
    val profileImageUrl: String,
) {
    @EmbeddedId
    val pk = CommonId()
    val timeStamp = Timestamp()
}
