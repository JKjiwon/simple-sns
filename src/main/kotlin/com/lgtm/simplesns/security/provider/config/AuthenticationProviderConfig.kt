package com.lgtm.simplesns.security.provider.config

import com.lgtm.simplesns.security.jwt.JwtUtils
import com.lgtm.simplesns.security.provider.JwtAuthenticationProvider
import com.lgtm.simplesns.security.userdetail.MemberDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AuthenticationProviderConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtAuthenticationProvider(
        jwtUtils: JwtUtils,
        memberDetailService: MemberDetailService
    ): JwtAuthenticationProvider {
        return JwtAuthenticationProvider(jwtUtils, memberDetailService)
    }

    @Bean
    fun daoAuthenticationProvider(
        passwordEncoder: PasswordEncoder,
        userDetailService: MemberDetailService
    ): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider(passwordEncoder)
        daoAuthenticationProvider.setUserDetailsService(userDetailService)
        return daoAuthenticationProvider
    }

    @Bean
    fun authenticationManager(
        jwtAuthenticationProvider: JwtAuthenticationProvider,
        daoAuthenticationProvider: DaoAuthenticationProvider
    ): AuthenticationManager {
        return ProviderManager(jwtAuthenticationProvider, daoAuthenticationProvider)
    }
}