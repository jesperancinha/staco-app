package org.jesperancinha.enterprise.engine.security.local.prod

import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
@Primary
@Profile("localprod && !test")
class AuthenticationProvider(userRepository: UserRepository, passwordEncoder: PasswordEncoder) :
    AuthenticationProvider {
    private val userRepository: UserRepository
    private val passwordEncoder: PasswordEncoder

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        if (authentication.name == null || authentication.credentials == null) {
            return throw RuntimeException("Credentials failure!")
        }
        if (authentication.name
                .isEmpty() || authentication.credentials
                .toString()
                .isEmpty()
        ) {
            throw RuntimeException("Credentials failure!")
        }
        val user: ApplicationUser? = userRepository.findUserByName(authentication.name)
        if (Objects.nonNull(user)) {
            val providedUser = authentication.name
            val providedUserPassword = authentication.credentials as String
            if (providedUser.equals(user!!.name, ignoreCase = true) && passwordEncoder.matches(
                    providedUserPassword,
                    user.password
                )
            ) {
                return UsernamePasswordAuthenticationToken(
                    user.email,
                    user.password,
                    setOf(SimpleGrantedAuthority(user.role))
                )
            }
        }
        throw UsernameNotFoundException("Invalid username/password.")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    init {
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
    }
}
