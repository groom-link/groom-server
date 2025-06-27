package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.Role
import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "authentication_roles")
data class AuthenticationRoleEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authentication_id")
    val authentication: AuthenticationEntity,
    @Enumerated(EnumType.STRING)
    val role: Role,
) {
    @EmbeddedId
    val pk = CommonId()
    val timeStamp = Timestamp()
    val authorityString get() = role.toString()
}
