package com.groom.auth.domain.authentication

enum class Role {
    USER,
    ADMIN,
    ;

    val authorityString get() = "ROLE_$name"
}
