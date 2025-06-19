package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.Authentication
import com.groom.auth.domain.authentication.AuthenticationRole
import com.groom.infrastructure.common.EntityTimeStamp
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
    val timeStamp = EntityTimeStamp()


    fun addRole(role: AuthenticationRole) {
        roles.add(AuthenticationRoleEntity(id, role))
    }

    fun of(authentication: Authentication): AuthenticationEntity {
        val roleEntities = authentication.roles
            .map { role -> AuthenticationRoleEntity.of(authentication.id, role) }
            .toMutableSet()
        return AuthenticationEntity(roleEntities)
    }

    fun toDomain(): Authentication {
        return Authentication(id = id,
            roles = roles.map { it.role }
                .toSet(),
            timestamp = timeStamp.toDomain())
    }
}