package org.jesperancinha.enterprise.staco.jpa.security.local.prod

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import java.sql.Timestamp
import java.time.LocalDateTime

@Configuration
@EnableAuthorizationServer
@Profile("localprod && !test")
class AuthorizationServerConfigurer(
    val authenticationManager: AuthenticationManager,
    val tokenStore: TokenStore,
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository
) :
    AuthorizationServerConfigurerAdapter() {


    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient(CLIENT_ID)
            .secret(passwordEncoder.encode(CLIENT_SECRET))
            .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN)
            .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
            .accessTokenValiditySeconds(VALID_FOREVER)
            .refreshTokenValiditySeconds(VALID_FOREVER)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore)
            .authenticationManager(authenticationManager)
            .allowedTokenEndpointRequestMethods()
    }

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.passwordEncoder(passwordEncoder)
            .allowFormAuthenticationForClients()
    }

    companion object {
        private const val CLIENT_ID = "stamps-and-coins-client"
        private const val CLIENT_SECRET = "stamps-and-coins"
        private const val GRANT_TYPE_PASSWORD = "password"
        private const val AUTHORIZATION_CODE = "authorization_code"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val SCOPE_READ = "read"
        private const val SCOPE_WRITE = "write"
        private const val TRUST = "trust"
        private const val VALID_FOREVER = -1
    }


    @Bean
    fun runner(): CommandLineRunner? {
        return CommandLineRunner {
            run {
                CoroutineScope(IO).launch {
                    val user = ApplicationUser()
                    user.name = "admin"
                    user.role = "ROLE_ADMIN"
                    user.password = passwordEncoder.encode("admin")
                    user.date = Timestamp.valueOf(LocalDateTime.now())
                    user.email = "thismail@thatmail.thatscope"
                    userRepository.save(user)
                }
            }
        }
    }
}