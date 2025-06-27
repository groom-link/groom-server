package com.groom.auth.domain.authentication

import com.groom.domain.Timestamp

data class Authentication(
    val id: Long,
    val roles: Set<Role>,
    val timestamp: Timestamp,
) {
    val claims: Map<String, String>
        get() =
            mutableMapOf(
                "sub" to id.toString(),
                "authorities" to
                    roles.joinToString(separator = ",", prefix = "ROLE_") {
                        it.name
                    },
            )
}
