package org.jesperancinha.enterprise.staco.blocking.security.local.prod

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler

@Configuration
@EnableResourceServer
@Profile("localprod && !test")
class ResourceServerConfigurer : ResourceServerConfigurerAdapter() {
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(RESOURCE_ID)
            .stateless(false)
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .requestMatchers("/**").hasRole("ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(OAuth2AccessDeniedHandler())
            .and().logout().logoutSuccessUrl("/").permitAll()
            .and()
            .csrf()
            .disable()
    }

    companion object {
        private const val RESOURCE_ID = "resource_id"
    }
}
