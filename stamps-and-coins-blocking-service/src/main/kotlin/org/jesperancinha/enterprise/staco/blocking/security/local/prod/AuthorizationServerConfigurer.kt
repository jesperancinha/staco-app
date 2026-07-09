package org.jesperancinha.enterprise.staco.blocking.security.local.prod

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.sql.Timestamp
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Configuration
@Profile("localprod && !test")
class AuthorizationServerConfigurer(
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository
) {

    @Bean
    @Order(1)
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer()
        http
            .securityMatcher(authorizationServerConfigurer.endpointsMatcher)
            .with(authorizationServerConfigurer) { it.oidc(Customizer.withDefaults()) }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .exceptionHandling {
                it.defaultAuthenticationEntryPointFor(
                    LoginUrlAuthenticationEntryPoint("/login"),
                    MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                )
            }
        return http.build()
    }

    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        val registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(CLIENT_ID)
            .clientSecret(passwordEncoder.encode(CLIENT_SECRET))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri(REDIRECT_URI)
            .scope(SCOPE_READ)
            .scope(SCOPE_WRITE)
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofDays(VALID_FOREVER_DAYS))
                    .refreshTokenTimeToLive(Duration.ofDays(VALID_FOREVER_DAYS))
                    .build()
            )
            .build()
        return InMemoryRegisteredClientRepository(registeredClient)
    }

    @Bean
    fun authorizationService(): OAuth2AuthorizationService = InMemoryOAuth2AuthorizationService()

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val keyPair = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }.generateKeyPair()
        val rsaKey = RSAKey.Builder(keyPair.public as RSAPublicKey)
            .privateKey(keyPair.private as RSAPrivateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
        return ImmutableJWKSet(JWKSet(rsaKey))
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings = AuthorizationServerSettings.builder().build()

    @Bean
    fun runner(): CommandLineRunner = CommandLineRunner {
        run {
            val user = ApplicationUser().apply {
                name = "admin"
                role = "ROLE_ADMIN"
                password = passwordEncoder.encode("admin")
                date = Timestamp.valueOf(LocalDateTime.now())
                email = "thismail@thatmail.thatscope"
            }
            userRepository.save(user)
        }
    }

    companion object {
        private const val CLIENT_ID = "stamps-and-coins-client"
        private const val CLIENT_SECRET = "stamps-and-coins"
        private const val REDIRECT_URI = "http://127.0.0.1:8080/authorized"
        private const val SCOPE_READ = "read"
        private const val SCOPE_WRITE = "write"
        private const val VALID_FOREVER_DAYS = 3650L
    }
}
