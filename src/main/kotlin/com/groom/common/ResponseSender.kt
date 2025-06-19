package com.groom.common

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component


@Component
class ResponseSender(private val objectMapper: ObjectMapper) {
    fun <T> send(response: HttpServletResponse, data: T) {
        val body: Response<T> = Response(data)
        val bodyString: String = objectMapper.writeValueAsString(body)
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer
            .write(bodyString)
    }
}