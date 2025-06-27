package com.groom.auth.domain.authentication

import com.groom.domain.CommonId
import com.groom.domain.Timestamp
import jakarta.persistence.CascadeType
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity(name = "authentications")
class Authentication(
    initialRole: Role = Role.USER,
) {
    @EmbeddedId
    val pk = CommonId()

    @OneToMany(mappedBy = "authentication", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    private val authenticationRoles: MutableSet<AuthenticationRole> =
        mutableSetOf(AuthenticationRole(this, initialRole))
    val timeStamp = Timestamp()

    val roles get() = authenticationRoles.map { it.role }

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
