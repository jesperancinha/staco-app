package org.jesperancinha.enterprise.staco.blocking.security.local.prod

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableResourceServer
@Profile("localprod && !test")
class ResourceServerConfigurer : ResourceServerConfigurerAdapter() {
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(RESOURCE_ID)
            .stateless(false)
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .formLogin { }   // replaces .formLogin()
            .exceptionHandling { exceptions ->
                exceptions.accessDeniedHandler(OAuth2AccessDeniedHandler())
            }
            .logout { logout ->
                logout
                    .logoutSuccessUrl("/")
                    .permitAll()
            }
            .csrf { it.disable() }
    }

    companion object {
        private const val RESOURCE_ID = "resource_id"
    }
}
