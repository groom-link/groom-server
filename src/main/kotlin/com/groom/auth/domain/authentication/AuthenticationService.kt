package com.groom.auth.domain.authentication

import org.springframework.stereotype.Component

@Component
class AuthenticationService internal constructor(
    private val authenticationRepository: AuthenticationRepository,
) {
    fun read(id: Long): Authentication = authenticationRepository.findBy(id)

    fun create(initializeRole: Role = Role.USER): Authentication = authenticationRepository.create(initializeRole)
}
