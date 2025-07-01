package com.groom.application.auth.required

import com.groom.application.auth.provided.AuthCommand

interface OAuth2UserInfoFinder {
    fun find(command: AuthCommand.OAuth2LoginWithAccessToken): Map<String, Any>
}