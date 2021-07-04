package org.jesperancinha.demo.engine.security.local

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager

@Profile("!prod && !localprod && !test")
@Configuration
@EnableWebSecurity
class ConfigurationAdapter(
    private val jdbcUserDetailsManager: JdbcUserDetailsManager,
    private val passwordEncoder: PasswordEncoder
) :
    WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/**")
            .authenticated()
            .and()
            .formLogin()
            .and().csrf().disable()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(jdbcUserDetailsManager).passwordEncoder(passwordEncoder)
    }
}