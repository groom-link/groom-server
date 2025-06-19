package com.groom.auth.interfaces.handler

import com.groom.common.ErrorResponse
import com.groom.common.ResponseSender
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
internal class SecurityHandler (
    oAuth2Handler: OAuth2Handler,
    private val responseSender: ResponseSender,
) {
    val successLoginHandler = oAuth2Handler.oAuth2successLoginHandler
    val entryPoint =
        { _: HttpServletRequest, response: HttpServletResponse, e: RuntimeException ->
            response.status = 401
            responseSender.send(response, ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), e))
        }

    val accessDeniedHandler = { _: HttpServletRequest, response: HttpServletResponse,
                                e: RuntimeException ->
        response.status = 403
        responseSender.send(response, ErrorResponse.of(HttpStatus.FORBIDDEN.value(), e))
    }
}