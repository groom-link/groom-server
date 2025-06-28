package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class AuthenticationCoreRepository(
    private val jpaRepository: AuthenticationJpaRepository,
): AuthenticationRepository {
    override fun findBy(providerName: OAuth2ProviderName, providerUserId: String): Authentication? {
        return jpaRepository.findByOAuthInfoWithRole(providerName, providerUserId)
    }

    @Transactional
    override fun createWithOAuth2(create: CreateOAuth2Authentication): Authentication {
        val entity = Authentication()
        entity.registerOAuth2(create)
        return jpaRepository.save(entity)
    }
}

@Repository
internal interface AuthenticationJpaRepository : JpaRepository<Authentication, Long> {
    @Query("select u from authentications u right join oauth2_authentications o join fetch u.authenticationRoles where o.providerName = :providerName and o.providerUserId = :providerUserId")
    fun findByOAuthInfoWithRole(providerName: OAuth2ProviderName, providerUserId: String): Authentication?
}
