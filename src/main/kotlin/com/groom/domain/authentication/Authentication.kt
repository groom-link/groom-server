package com.groom.domain.authentication

import com.groom.application.auth.provided.AuthCommand
import com.groom.domain.shared.CommonEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany


@Entity(name = "authentications")
class Authentication(
    initialRole: Role = Role.USER,
): CommonEntity() {

    @OneToMany(mappedBy = "authentication", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    private val authenticationRoles: MutableSet<AuthenticationRole> =
        mutableSetOf(AuthenticationRole(this, initialRole))

    @OneToMany(mappedBy = "authentication", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val oAuth2Authentications = mutableSetOf<OAuth2Authentication>()

    val roles get() = authenticationRoles.map { it.role }

    fun registerOAuth2(create: AuthCommand.CreateOAuth2Authentication) {
        oAuth2Authentications.add(OAuth2Authentication.forRegister(create, this))
    }

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
