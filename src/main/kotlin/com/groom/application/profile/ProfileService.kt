package com.groom.application.profile

import com.groom.application.profile.required.ProfileRepository
import com.groom.application.profile.provided.CreateProfile
import com.groom.application.profile.provided.ProfileCommand
import com.groom.domain.profile.Profile
import org.springframework.stereotype.Service

@Service
class ProfileService(private val profileRepository: ProfileRepository) : CreateProfile {
    override fun create(command: ProfileCommand.Create) {
        profileRepository.save(Profile.from(command))
    }
}