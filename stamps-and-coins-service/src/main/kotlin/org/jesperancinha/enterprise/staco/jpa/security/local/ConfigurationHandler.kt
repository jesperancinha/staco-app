package org.jesperancinha.enterprise.staco.jpa.security.local

import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.sql.DataSource

@Configuration
class ConfigurationHandler(
    val applicationContext: ApplicationContext,
    val jdbcUserDetailsManager: JdbcUserDetailsManager,
    val passwordEncoder: PasswordEncoder,
    val dataSource: DataSource
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication
    ) {
        httpServletResponse.sendRedirect("/search")
    }

    @Bean
    fun runner(): CommandLineRunner? {
        return CommandLineRunner {
            run {
                val resource = applicationContext.getResource("classpath:schema.sql")
                ScriptUtils.executeSqlScript(dataSource.connection, resource)
                jdbcUserDetailsManager.createUser(
                    User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build()
                )
            }
        }
    }

}