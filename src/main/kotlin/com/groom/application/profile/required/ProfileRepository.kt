package com.groom.application.profile.required

import com.groom.domain.profile.Profile
import com.groom.domain.shared.PK
import org.springframework.data.repository.Repository

interface ProfileRepository: Repository<Profile, PK> {
    fun save(profile: Profile): Profile
}