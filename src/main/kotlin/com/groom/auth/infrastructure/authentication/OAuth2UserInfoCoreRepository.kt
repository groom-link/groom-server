package com.groom.auth.infrastructure.authentication

import com.groom.auth.domain.authentication.OAuth2ProviderName
import com.groom.auth.domain.authentication.OAuth2UserInfo
import com.groom.auth.domain.authentication.OAuth2UserInfoCommand
import com.groom.auth.domain.authentication.OAuth2UserInfoRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class OAuth2UserInfoCoreRepository(
    private val jpaRepository: OAuth2UserInformationJpaRepository,
    private val oAuth2UserInfoFactory: OAuth2UserInfoFactory,
) : OAuth2UserInfoRepository {
    override fun findBy(
        providerName: OAuth2ProviderName,
        providerUserId: String,
    ): OAuth2UserInfo? =
        jpaRepository
            .findByProviderNameAndProviderUserId(providerName, providerUserId)

    override fun create(
        authenticationId: Long,
        data: OAuth2UserInfoCommand.Create,
    ): OAuth2UserInfo {
        val entity = oAuth2UserInfoFactory.create(authenticationId, data)
        return jpaRepository.save(entity)
    }
}

@Repository
internal interface OAuth2UserInformationJpaRepository : JpaRepository<OAuth2AuthenticationEntity, Long> {
    fun findByProviderNameAndProviderUserId(
        providerName: OAuth2ProviderName,
        providerUserId: String,
    ): OAuth2AuthenticationEntity?
}
