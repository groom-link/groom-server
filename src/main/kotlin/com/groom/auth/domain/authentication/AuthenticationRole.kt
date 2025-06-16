package com.groom.auth.domain.authentication

enum class AuthenticationRole {
    USER, ADMIN;

    fun toAuthorityString(): String {
        return "ROLE_$name"
    }
}