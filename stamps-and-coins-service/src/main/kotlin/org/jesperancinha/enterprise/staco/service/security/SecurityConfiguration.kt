package org.jesperancinha.enterprise.staco.service.security

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class SecurityConfiguration(
    @Value("\${org.jesperancinha.enterprise.staco.root.name}")
    private val username: String,
    @Value("\${org.jesperancinha.enterprise.staco.root.password}")
    private val password: String
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService? {
        val user: UserDetails = User.withUsername(username)
            .passwordEncoder { passwordEncoder.encode(it) }
            .password(password)
            .roles("USER")
            .build()
        logger.info { "Your root user has been registered: $user" }
        return MapReactiveUserDetailsService(user)
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http
            .authorizeExchange()
            .pathMatchers("/webjars/**")
            .permitAll()
            .pathMatchers("/swagger-ui/**")
            .permitAll()
            .and()
            .httpBasic(withDefaults())
            .formLogin(withDefaults())
            .csrf().disable()
        return http.build()
    }
}