package org.jesperancinha.enterprise.staco.jpa.security.local

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import javax.sql.DataSource


@Profile("!prod && !localprod && !test")
@Configuration
class SecurityConfiguration(
    val dataSource: DataSource
) {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun jdbcUserDetailsManager(): JdbcUserDetailsManager? {
        return JdbcUserDetailsManager(dataSource)
    }
}