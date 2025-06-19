package com.groom.auth.domain.authentication

import org.springframework.stereotype.Component

@Component
class AuthenticationService internal constructor(private val authenticationRepository: AuthenticationRepository) {
    fun read(id: Long): Authentication = authenticationRepository.findBy(id)
    fun create(initializeRole: AuthenticationRole = AuthenticationRole.USER): Authentication {
        return authenticationRepository.create(initializeRole)
    }
}