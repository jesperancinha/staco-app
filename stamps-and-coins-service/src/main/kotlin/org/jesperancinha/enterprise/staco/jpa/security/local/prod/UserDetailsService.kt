package org.jesperancinha.enterprise.staco.jpa.security.local.prod

import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Profile
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
@Profile("localprod && !test")
class UserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails = runBlocking {
        userRepository.findById(username)?.let {
            createUserDetails(it)
        } ?: throw RuntimeException("Credentials failure!")
    }

    private fun createUserDetails(applicationUser: ApplicationUser): User {
        return User(
            applicationUser.email,
            applicationUser.password,
            listOf(SimpleGrantedAuthority(applicationUser.role))
        )
    }
}