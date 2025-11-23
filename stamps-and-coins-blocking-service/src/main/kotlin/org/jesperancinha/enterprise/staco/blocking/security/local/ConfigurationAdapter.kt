package org.jesperancinha.enterprise.staco.blocking.security.local

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Profile("!prod && !localprod && !test")
@Configuration
@EnableWebSecurity
class ConfigurationAdapter(
    private val jdbcUserDetailsManager: JdbcUserDetailsManager
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .userDetailsService(jdbcUserDetailsManager)
        .authorizeHttpRequests { auth ->
            auth
                .requestMatchers("/**").authenticated()
        }
        .formLogin {}
        .csrf { it.disable() }
        .build()
}
