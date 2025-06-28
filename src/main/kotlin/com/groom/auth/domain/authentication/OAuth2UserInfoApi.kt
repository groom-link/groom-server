package com.groom.auth.domain.authentication

interface OAuth2UserInfoApi {
    fun find(command: AuthCommand.OAuth2LoginWithAccessToken): Map<String, Any>
}