package com.groom.domain.profile

import com.groom.application.profile.provided.ProfileCommand
import com.groom.domain.shared.CommonEntity
import com.groom.domain.shared.PK
import jakarta.persistence.Entity
import org.hibernate.annotations.NaturalId


@Entity(name = "profiles")
class Profile(
    @NaturalId
    val authenticationPk: PK,
//    val email: String, TODO: 사업자 등록후 가능
    @NaturalId
    val nickname: String,
    val profileImageUrl: String,
): CommonEntity() {

    companion object {
        fun from(create: ProfileCommand.Create) = Profile(
            authenticationPk = create.authenticationPk,
            nickname = create.nickname,
            profileImageUrl = create.profileImageUrl,
        )
    }
}
