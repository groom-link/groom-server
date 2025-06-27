package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.Role
import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.CascadeType
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity(name = "authentications")
class AuthenticationEntity(
    initialRole: Role = Role.USER,
) {
    @EmbeddedId
    val pk = CommonId()

    @OneToMany(mappedBy = "authentication", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val roles: MutableSet<AuthenticationRoleEntity> =
        mutableSetOf(AuthenticationRoleEntity(this, initialRole))
    val timeStamp = Timestamp()

    val claims: Map<String, String>
        get() =
            mutableMapOf(
                "sub" to pk.toString(),
                "authorities" to
                    roles.joinToString(separator = ",") {
                        it.authorityString
                    },
            )
}
