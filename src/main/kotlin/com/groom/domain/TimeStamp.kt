package com.groom.domain

import java.time.LocalDateTime

data class TimeStamp(val createdAt: LocalDateTime,
                     val updatedAt: LocalDateTime)