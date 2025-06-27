package com.groom.auth.domain.authentication

enum class Role {
    USER,
    ADMIN,
    ;

    override fun toString(): String = "ROLE_$name"
}
