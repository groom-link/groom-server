package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.Authentication
import com.groom.auth.domain.authentication.AuthenticationRepository
import com.groom.auth.domain.authentication.AuthenticationRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class AuthenticationCoreRepository (private val jpaRepository: AuthenticationJpaRepository) :
    AuthenticationRepository {
    @Transactional
    override fun create(initialRole: AuthenticationRole): Authentication {
        val entity = AuthenticationEntity()
        val authentication = jpaRepository.save(entity)
        authentication.addRole(initialRole)
        return authentication.toDomain()
    }

    override fun findBy(id: Long): Authentication {
        return jpaRepository.findByIdWithRole(id)
            .toDomain()
    }
}

@Repository
internal interface AuthenticationJpaRepository : JpaRepository<AuthenticationEntity, Long> {
    @Query("select u from authentications u join fetch u.roles where u.id = :id")
    fun findByIdWithRole(id: Long): AuthenticationEntity
}