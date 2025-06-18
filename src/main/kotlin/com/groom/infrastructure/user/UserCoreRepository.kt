package com.groom.infrastructure.user

import com.groom.domain.user.User
import com.groom.domain.user.UserCommand
import com.groom.domain.user.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class UserCoreRepository (private val jpaRepository: UserJpaRepository) :
    UserRepository {
    override fun createUser(command: UserCommand.Create): User {
        val entity = UserEntity.fromCreate(command)
        return jpaRepository.save(entity)
            .toDomain()
    }
}

@Repository
internal interface UserJpaRepository : JpaRepository<UserEntity, Long>