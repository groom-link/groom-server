package com.groom.domain.shared

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

typealias PK = Long

@MappedSuperclass
abstract class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: PK = 0
    val timestamp = Timestamp()
}