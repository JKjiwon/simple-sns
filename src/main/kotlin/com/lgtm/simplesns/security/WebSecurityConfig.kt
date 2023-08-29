package com.lgtm.simplesns.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class WebSecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests.anyRequest().permitAll()
            }
        http
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        http
            .addFilterAfter(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    @Bean
    fun jwtAuthenticationFilter(jwtAuthenticationProvider: JwtAuthenticationProvider): JwtAuthenticationFilter {
        val authenticationManager = ProviderManager(jwtAuthenticationProvider)
        return JwtAuthenticationFilter(authenticationManager)
    }

    @Bean
    fun devWebSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(PathRequest.toH2Console())
        }
    }
}