package com.groom

import com.groom.common.util.JwtUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
    JwtUtil.saveKeyPairFileIfNotExists()
    runApplication<ApiApplication>(*args)
}