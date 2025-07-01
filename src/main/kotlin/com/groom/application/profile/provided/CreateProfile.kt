package com.groom.application.profile.provided

import com.groom.domain.shared.PK


interface CreateProfile {
    fun create(command: ProfileCommand.Create)
}

class ProfileCommand private constructor() {
    data class Create(
        val authenticationPk: PK,
//        val email: String, TODO: 사업자 등록후 가능
        val nickname: String,
        val profileImageUrl: String,
    )
}