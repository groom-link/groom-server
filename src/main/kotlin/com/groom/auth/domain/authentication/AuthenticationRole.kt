package com.groom.auth.domain.authentication

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
data class AuthenticationRole(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authentication_id")
    val authentication: Authentication,
    @Enumerated(EnumType.STRING)
    val role: Role,
) {
    @EmbeddedId
    val pk = CommonId()
    val timeStamp = Timestamp()
}
