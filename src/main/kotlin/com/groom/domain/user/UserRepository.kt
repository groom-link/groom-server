package com.groom.domain.user

interface UserRepository {
    fun createUser(command: UserCommand.Create): User
}