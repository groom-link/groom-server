package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.AuthenticationRole
import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity(name = "authentication_roles")
class AuthenticationRoleEntity(
    val authenticationId: Long,
    @Enumerated(EnumType.STRING)
    val role: AuthenticationRole,
) {
    @EmbeddedId
    val pk = CommonId()
    val timeStamp = Timestamp()
}
