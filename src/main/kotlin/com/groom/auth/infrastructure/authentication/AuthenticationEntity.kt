package com.groom.auth.infrastructure.authentication

import com.groom.domain.Timestamp
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "authentications")
internal class AuthenticationEntity(
    @OneToMany(mappedBy = "authenticationId", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val roles: MutableSet<AuthenticationRoleEntity> = mutableSetOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    val timeStamp = Timestamp()
}
