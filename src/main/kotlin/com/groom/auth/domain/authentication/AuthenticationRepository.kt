package com.groom.auth.domain.authentication

interface AuthenticationRepository {
    fun create(initialRole: AuthenticationRole = AuthenticationRole.USER): Authentication
    fun findBy(id: Long): Authentication
}