package com.lgtm.simplesns.security.config

import com.lgtm.simplesns.security.filter.JwtAuthenticationFilter
import com.lgtm.simplesns.security.provider.JwtAuthenticationProvider
import com.lgtm.simplesns.security.userdetail.MemberDetailService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher

private val SWAGGER_PATH_PATTERNS = arrayOf(
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/v3/api-docs/**"
)

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class WebSecurityConfig(
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.authorizeHttpRequests {
            it.requestMatchers(antMatcher("/open-api/**")).permitAll()
                .anyRequest().authenticated()
        }

        http
            .addFilterAfter(
                jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        val authenticationManager = ProviderManager(jwtAuthenticationProvider)
        return JwtAuthenticationFilter(authenticationManager)
    }

    @Bean
    fun daoAuthenticationProvider(userDetailService: MemberDetailService): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider(passwordEncoder())
        daoAuthenticationProvider.setUserDetailsService(userDetailService)
        return daoAuthenticationProvider
    }

    @Bean
    fun devWebSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            val swaggerPathMatcher = SWAGGER_PATH_PATTERNS.map { antMatcher(it) }.toTypedArray()
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(*swaggerPathMatcher)
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}