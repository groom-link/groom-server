package com.groom.auth

import com.groom.auth.domain.authentication.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

class CustomOAuth2User(val authentication: Authentication,
                       attributes: MutableMap<String, Any>, nameAttributeKey: String) :
    DefaultOAuth2User(
        authentication.roles.map { SimpleGrantedAuthority(it.toAuthorityString()) }, attributes,
        nameAttributeKey)