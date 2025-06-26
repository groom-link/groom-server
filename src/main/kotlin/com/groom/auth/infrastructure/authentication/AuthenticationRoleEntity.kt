package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.AuthenticationRole
import com.groom.domain.Timestamp
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "authentication_roles")
internal class AuthenticationRoleEntity internal constructor(
    val authenticationId: Long,
    @Enumerated(EnumType.STRING)
    val role: AuthenticationRole,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
    val timeStamp = Timestamp()
}
