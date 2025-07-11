package com.groom.domain.authentication

enum class Role {
    USER,
    ;

    val authorityString get() = "ROLE_$name"
}
