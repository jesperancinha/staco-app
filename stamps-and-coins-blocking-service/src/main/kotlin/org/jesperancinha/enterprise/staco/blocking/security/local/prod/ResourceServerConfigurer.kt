package org.jesperancinha.enterprise.staco.blocking.security.local.prod

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.web.SecurityFilterChain

@Configuration
@Profile("localprod && !test")
class ResourceServerConfigurer(
    private val authenticationProvider: AuthenticationProvider,
    private val userDetailsService: UserDetailsService
) {

    @Bean
    @Order(2)
    fun resourceServerFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .userDetailsService(userDetailsService)
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
            .exceptionHandling { it.accessDeniedHandler(BearerTokenAccessDeniedHandler()) }
            .logout { it.logoutSuccessUrl("/").permitAll() }
            .csrf { it.disable() }
        return http.build()
    }
}
