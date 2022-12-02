package org.jesperancinha.enterprise.staco.blocking.security.prod

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Profile("prod && !test")
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .oauth2Login()
        .and()
        .logout()
        .and()
        .csrf()
        .disable().build()
}
