package com.groom.auth.domain.authentication

interface AuthenticationRepository {
    fun create(initialRole: Role = Role.USER): Authentication

    fun findBy(id: Long): Authentication
}
