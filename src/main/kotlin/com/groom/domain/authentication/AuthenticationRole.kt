package com.groom.domain.authentication

import com.groom.domain.shared.CommonEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "authentication_roles")
data class AuthenticationRole(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authentication_id")
    val authentication: Authentication,
    @Enumerated(EnumType.STRING)
    val role: Role,
): CommonEntity()
