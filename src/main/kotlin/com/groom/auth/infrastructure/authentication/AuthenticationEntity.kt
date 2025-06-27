package com.groom.auth.infrastructure.authentication

import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.CascadeType
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity(name = "authentications")
class AuthenticationEntity(
    @OneToMany(mappedBy = "authenticationId", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val roles: MutableSet<AuthenticationRoleEntity> = mutableSetOf(),
) {
    @EmbeddedId
    val id = CommonId()
    val timeStamp = Timestamp()
}
