package com.groom.auth.interfaces.api

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

internal class CustomOAuth2User(
    val authentication: Authentication,
    attributes: MutableMap<String, Any>,
    nameAttributeKey: String,
) : DefaultOAuth2User(
        authentication.roles.map { SimpleGrantedAuthority(it.toAuthorityString()) },
        attributes,
        nameAttributeKey,
    )
