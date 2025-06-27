package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.Authentication
import com.groom.auth.domain.authentication.AuthenticationRepository
import com.groom.auth.domain.authentication.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class AuthenticationCoreRepository(
    private val jpaRepository: AuthenticationJpaRepository,
) : AuthenticationRepository {
    @Transactional
    override fun create(initialRole: Role): Authentication {
        val entity = Authentication()
        val authentication = jpaRepository.save(entity)
        authentication.addRole(initialRole)
        return authentication.toDomain()
    }

    override fun findBy(id: Long): Authentication =
        jpaRepository
            .findByIdWithRole(id)
            .toDomain()
}

@Repository
internal interface AuthenticationJpaRepository : JpaRepository<Authentication, Long> {
    @Query("select u from authentications u join fetch u.roles where u.id = :id")
    fun findByIdWithRole(id: Long): Authentication
}
