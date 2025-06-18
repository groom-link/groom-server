package com.groom.auth

import com.groom.auth.interfaces.handler.SecurityHandler
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.disable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


private const val OAUTH2_AUTHORIZATION_CODE_LOGIN_PATH = "/api/v1/auth/login/oauth2/code/*"
private const val OAUTH2_LOGIN_PAGE_PATH_BASE_PATH = "/api/v1/auth/login/oauth2/authorization"

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true)
class SecurityConfig(
    private val handler: SecurityHandler,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { disable() }
        http.cors { disable() }
        http.formLogin { disable() }
        http.oauth2Login {
            it.authorizationEndpoint{endPoint ->
                endPoint.baseUri(OAUTH2_LOGIN_PAGE_PATH_BASE_PATH)
            }
            it.loginProcessingUrl(OAUTH2_AUTHORIZATION_CODE_LOGIN_PATH)
            it.successHandler(handler.successLoginHandler)
        }
        http.exceptionHandling {
            it.accessDeniedHandler(handler.accessDeniedHandler)
            it.authenticationEntryPoint(handler.entryPoint)
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.addAllowedOrigin("*")
        source.registerCorsConfiguration("/**", config)
        return source
    }
}