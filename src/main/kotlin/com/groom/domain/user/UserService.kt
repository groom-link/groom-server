package com.groom.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun create(command: UserCommand.Create): User {
        return userRepository.createUser(command)
    }
}