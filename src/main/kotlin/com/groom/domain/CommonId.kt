package com.groom.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Embeddable
data class CommonId(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
)
