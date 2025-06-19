package com.groom.infrastructure.common

import com.groom.domain.TimeStamp
import jakarta.persistence.Embeddable
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Embeddable
class EntityTimeStamp {
    @CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    fun toDomain(): TimeStamp {
        return TimeStamp(createdAt, updatedAt)
    }
}