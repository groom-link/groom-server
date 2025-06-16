package com.groom.auth.infrastructure.oauth2

import com.groom.auth.domain.oauth2.OAuth2ProviderName
import com.groom.auth.domain.oauth2.OAuth2UserInformation
import com.groom.auth.domain.oauth2.OAuth2UserInformationCommand
import com.groom.auth.domain.oauth2.OAuth2UserInformationRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class OAuth2UserInformationCoreRepository internal constructor(private val jpaRepository: OAuth2UserInformationJpaRepository,
                                                               private val oAuth2VOMapper: OAuth2VOMapper):
    OAuth2UserInformationRepository {
    override fun findBy(providerName: OAuth2ProviderName,
                        providerUserId: String): OAuth2UserInformation? {
        return jpaRepository.findByProviderNameAndProviderUserId(providerName, providerUserId)
            ?.toDomain()
    }

    override fun create(data: OAuth2UserInformationCommand.Create): OAuth2UserInformation {
        val information = oAuth2VOMapper.convert(data.providerName, data.attributes)
        val entity = OAuth2UserInformationEntity.create(data.authenticationId, information)
        return jpaRepository.save(entity)
            .toDomain()
    }
}

@Repository
internal interface OAuth2UserInformationJpaRepository :
    JpaRepository<OAuth2UserInformationEntity, Long> {
    fun findByProviderNameAndProviderUserId(providerName: OAuth2ProviderName,
                                            providerUserId: String): OAuth2UserInformationEntity?
}