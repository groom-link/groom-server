package com.groom.application.auth.required

import com.groom.domain.authentication.Authentication
import com.groom.domain.authentication.OAuth2ProviderName
import com.groom.domain.shared.PK
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository


interface AuthenticationRepository: Repository<Authentication, PK> {
    @Query("select u from authentications u join oauth2_authentications o join fetch u.authenticationRoles where o.providerName = :providerName and o.providerUserId = :providerUserId")
    fun findBy(providerName: OAuth2ProviderName, providerUserId: String): Authentication?
    fun save(authentication: Authentication): Authentication
}